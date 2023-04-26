package com.geulkkoli.domain.post;

import com.geulkkoli.domain.user.ImplUserRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PostRepositoryTest {

    /**
     * 단위 테스트 구현을 위한 구현체
     */
    @Autowired
    private PostRepository implPostRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @AfterEach
    void afterEach () {
        implPostRepository.clear();
    }

    @BeforeEach
    void init(){
        User save = User.builder()
                .email("test@naver.com")
                .userName("test")
                .nickName("test")
                .phoneNo("00000000000")
                .password("123")
                .gender("male").build();

        user = userRepository.save(save);
    }

    @BeforeEach
    void beforeEach () {

        Post save01 = implPostRepository.save(Post.builder()
                .nickName("바나나")
                .postBody("나는 멋지고 섹시한 개발자")//채&훈
                .title("여러분").build()
        );
        Post save02 = implPostRepository.save(Post.builder()
                .title("testTitle01")
                .postBody("test postbody 01")
                .nickName("점심뭐먹지").build()
        );
        Post save03 = implPostRepository.save(Post.builder()
                .title("testTitle02")
                .postBody("test postbody 02")
                .nickName("점심뭐먹지").build()
        );
        Post save04 = implPostRepository.save(Post.builder()
                .title("testTitle03")
                .postBody("test postbody 03")
                .nickName("점심뭐먹지").build()
        );
        save01.addAuthor(user);
        save02.addAuthor(user);
        save03.addAuthor(user);
        save04.addAuthor(user);
    }
    private TestEntityManager em;

    @Test
    void save() {
        Post post = new Post("title", "body", "nick");
        post.addAuthor(user);
        Post save = implPostRepository.save(post);
        Assertions.assertThat(save.getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    void saveWithEmptyUser () {
        User user = User.builder()
                .userName("test")
                .nickName("test")
                .phoneNo("00000000000")
                .password("123")
                .gender("male").build();
        Post post = new Post("title", "body", "nick");
        post.addAuthor(user);
        Post save = implPostRepository.save(post);
        Assertions.assertThat(save.getTitle()).isEqualTo(post.getTitle());
        em.flush();

        assertThat(save).isEqualTo(post);
    }

    @Test
    void findById() {
        Post post = new Post("title", "body", "nick");
        post.addAuthor(user);
        Post save = implPostRepository.save(post);
        em.flush();
        Optional<Post> find = implPostRepository.findById(save.getPostId());
        assertThat(save).isEqualTo(find.get());
        Post find = implPostRepository.findById(save.getPostId())
                .orElseThrow(()->new NoSuchElementException("No post found id matches : "+save.getPostId()));
        Assertions.assertThat(save).isEqualTo(find);
    }

    @Test
    void findAll() {
        Post post = new Post(1L, "title02", "body02", "nick02");
        implPostRepository.save(post);
        Post post2 = new Post(2L, "title02", "body02", "nick02");
        implPostRepository.save(post2);
        List<Post> all = implPostRepository.findAll();
        assertThat(2).isEqualTo(all.size());
    }


    /**
     * TODO
     *  영속성 컨텍스트에 반영되지 않아 실패하지만 다음에 고쳐보겠다.
     *  단일 테스트는 통과하지만 전체를 돌렸을 때는 empty를 반환한다.
     */
    @Test
    void update() {

        Post post = new Post(1L, "title02", "body02", "nick02");
        Post updatePost = new Post(1L, "title03", "body02", "nick02");

        implPostRepository.save(post);

        em.flush();
        em.clear();

        implPostRepository.update(1L, updatePost);

        Optional<Post> findById = implPostRepository.findById(1L);
        assertThat(findById).get().hasFieldOrPropertyWithValue("title", "title03");
        Post savePost = implPostRepository.save(new Post("new01", "newBody01", "newNick01"));
        savePost.addAuthor(user);
        Post update = new Post();
        update.setTitle("update");
        update.setPostBody("updateBody");
        implPostRepository.update(savePost.getPostId(), update);
        Post one = implPostRepository.findById(savePost.getPostId())
                .orElseThrow(()->new NoSuchElementException("No post found id matches : "+savePost.getPostId()));
        Assertions.assertThat(one.getTitle()).isEqualTo(update.getTitle());
        Assertions.assertThat(one.getPostBody()).isEqualTo(update.getPostBody());
    }

    @Test
    void delete() {
        Post update = new Post(1L, "title02", "body02", "nick02");
        implPostRepository.save(update);
        implPostRepository.delete(1L);
        Post deletePost = implPostRepository.save(new Post("deleteTitle", "deleteBody01", user.getNickName()));
        deletePost.addAuthor(user);
        implPostRepository.delete(deletePost.getPostId());
        List<Post> all = implPostRepository.findAll();

        assertThat(all.size()).isEqualTo(0);
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}