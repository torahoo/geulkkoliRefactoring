package com.geulkkoli.domain.post;

import com.geulkkoli.domain.post.entity.Post;
import com.geulkkoli.domain.user.ImplUserRepository;
import com.geulkkoli.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired
    private ImplPostRepository implPostRepository;
    @Autowired
    private ImplUserRepository impluserRepository;


    @AfterEach
    void afterEach () {
        implPostRepository.clear();
    }

    @BeforeEach
    void beforeEach () {
        implPostRepository.save(Post.builder()
                .authorId(1L)
                .nickName("바나나")
                .postBody("나는 멋지고 섹시한 개발자")//채&훈
                .title("여러분").build()
        );
        implPostRepository.save(Post.builder()
                .authorId(2L)
                .title("testTitle01")
                .postBody("test postbody 01")
                .nickName("점심뭐먹지").build()
        );
        implPostRepository.save(Post.builder()
                .authorId(2L)
                .title("testTitle02")
                .postBody("test postbody 02")
                .nickName("점심뭐먹지").build()
        );
        implPostRepository.save(Post.builder()
                .authorId(2L)
                .title("testTitle03")
                .postBody("test postbody 03")
                .nickName("점심뭐먹지").build()
        );

        impluserRepository.save(User.builder()
                .email("tako@naver.com")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .phoneNo("01012345678")
                .gender("male")
                .build());

        impluserRepository.save(User.builder()
                .email("test@naver.com")
                .userName("홍길동")
                .nickName("점심뭐먹지")
                .password("1111")
                .phoneNo("01011112222")
                .gender("female")
                .build());
    }

    @Test
    void save() {
        Post post = new Post(1L, "title", "body", "nick");
        Post save = implPostRepository.save(post);
        Assertions.assertThat(save).isEqualTo(post);
    }

    @Test
    void findById() {
        Post post = new Post(1L, "title", "body", "nick");
        Post save = implPostRepository.save(post);
        Optional<Post> find = implPostRepository.findById(save.getPostId());
        Assertions.assertThat(save).isEqualTo(find.get());
    }

    @Test
    void findAll() {
        List<Post> all = implPostRepository.findAll();
        Assertions.assertThat(4).isEqualTo(all.size());
    }

    @Test
    void update() {
        Post savePost = implPostRepository.save(new Post(1L, "new01", "newBody01", "newNick01"));
        Post update = new Post();
        update.setTitle("update");
        update.setPostBody("updateBody");
        implPostRepository.update(savePost.getPostId(), update);
        Optional<Post> one = implPostRepository.findById(savePost.getPostId());
        Assertions.assertThat(one.get().getTitle()).isEqualTo(update.getTitle());
    }

    @Test
    void delete() {
        implPostRepository.delete(1L);
        List<Post> all = implPostRepository.findAll();

        Assertions.assertThat(all.size()).isEqualTo(3);
    }
}