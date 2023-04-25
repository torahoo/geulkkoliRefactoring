package com.geulkkoli.domain.post;

import com.geulkkoli.domain.post.entity.Post;
import com.geulkkoli.domain.post.repository.ImplPostRepository;
import com.geulkkoli.domain.user.ImplUserRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PostRepositoryTest {

    /**
     * 단위 테스트 구현을 위한 구현체
     */
    @Autowired
    private ImplPostRepository implPostRepository;

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
    }

    @Test
    void findById() {
        Post post = new Post("title", "body", "nick");
        post.addAuthor(user);
        Post save = implPostRepository.save(post);
        Post find = implPostRepository.findById(save.getPostId())
                .orElseThrow(()->new NoSuchElementException("No post found id matches : "+save.getPostId()));
        Assertions.assertThat(save).isEqualTo(find);
    }

    @Test
    void findAll() {
        List<Post> all = implPostRepository.findAll();
        Assertions.assertThat(4).isEqualTo(all.size());
    }

    @Test
    void update() {
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
        Post deletePost = implPostRepository.save(new Post("deleteTitle", "deleteBody01", user.getNickName()));
        deletePost.addAuthor(user);
        implPostRepository.delete(deletePost.getPostId());
        List<Post> all = implPostRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}