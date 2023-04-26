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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

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
        user.writePost(save01);
        user.writePost(save02);
        user.writePost(save03);
        user.writePost(save04);
    }

    @Test
    void save() {
        Post post = new Post("title", "body", "nick");
        Post save = implPostRepository.save(post);
        user.writePost(save);

        assertThat(save.getTitle()).isEqualTo(post.getTitle());
        assertThat(save.getPostBody()).isEqualTo("body");
    }

    @Test
    public void 유저로부터_작성한_게시글 () {
        //given
        Post save = implPostRepository.save(new Post("title", "body", "nick"));
        user.writePost(save);

        //when
        ArrayList<Post> authorPost = new ArrayList<>(user.getPosts());

        //then
        assertThat(authorPost.size()).isEqualTo(5);
        assertThat(authorPost.contains(save)).isEqualTo(true);
    }

    @Test
    void findById() {
        Post post = new Post("title", "body", "nick");
        Post save = implPostRepository.save(post);
        user.writePost(save);
        Post find = implPostRepository.findById(save.getPostId())
                .orElseThrow(()->new NoSuchElementException("No post found id matches : "+save.getPostId()));
        assertThat(save).isEqualTo(find);
    }

    @Test
    void findAll() {
        List<Post> all = implPostRepository.findAll();
        assertThat(4).isEqualTo(all.size());
    }

    @Test
    void update() {
        Post savePost = implPostRepository.save(new Post("new01", "newBody01", "newNick01"));
        user.writePost(savePost);
        Post update = new Post();
        update.setTitle("update");
        update.setPostBody("updateBody");
        implPostRepository.update(savePost.getPostId(), update);
        Post one = implPostRepository.findById(savePost.getPostId())
                .orElseThrow(()->new NoSuchElementException("No post found id matches : "+savePost.getPostId()));
        assertThat(one.getTitle()).isEqualTo(update.getTitle());
        assertThat(one.getPostBody()).isEqualTo(update.getPostBody());
    }

    @Test
    void delete() {
        //given
        Post deletePost = implPostRepository.save(new Post("deleteTitle", "deleteBody01", user.getNickName()));
        user.writePost(deletePost);

        implPostRepository.delete(deletePost.getPostId());
//        user.getPosts().remove(deletePost);

        //when
        List<Post> all = implPostRepository.findAll();
        ArrayList<Post> authorPost = new ArrayList<>(user.getPosts());

        //then
        assertThat(all.size()).isEqualTo(4);
        assertThat(authorPost.size()).isEqualTo(4);

    }

}