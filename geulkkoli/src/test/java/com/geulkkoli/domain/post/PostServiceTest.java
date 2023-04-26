package com.geulkkoli.domain.post;

import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.post.dto.AddDTO;
import com.geulkkoli.web.post.dto.ListDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TestDataInit Class 참조
 */
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;


    @Test
    @Transactional
    void findById() {
        User user1 = User.builder()
                .email("email@email.com")
                .userName("userName")
                .gender("gender")
                .password("password")
                .phoneNo("phoneNo")
                .nickName("nickName")
                .build();
        postService.savePost(new AddDTO(1L, "title", "body", "nick"), user1);
        Post post = postService.findById(1L);

        assertThat("title").isEqualTo(post.getTitle());
    }

    @Test
    @Transactional
    void findAll() {
        User user1 = User.builder()
                .email("email@email.com")
                .userName("userName")
                .gender("gender")
                .password("password")
                .phoneNo("phoneNo")
                .nickName("nickName")
                .build();

        userRepository.save(user1);

        postService.savePost(new AddDTO(1L, "title", "body", "nick"), user1);
        postService.savePost(new AddDTO(1L, "title", "body", "nick"), user1);
        postService.savePost(new AddDTO(1L, "title", "body", "nick"), user1);
        postService.savePost(new AddDTO(1L, "title", "body", "nick"), user1);


        assertThat(postService.findAll().size()).isEqualTo(4);
    }

    @Test
    @Transactional
    void updatePost() {
        User user1 = User.builder()
                .email("email@email.com")
                .userName("userName")
                .gender("gender")
                .password("password")
                .phoneNo("phoneNo")
                .nickName("nickName")
                .build();

        userRepository.save(user1);

        Post update = Post.builder()
                .title("title update")
                .postBody("postBody update")
                .nickName("nickName update")
                .build();
        postService.savePost(new AddDTO(1L, "title", "body", "nick"), user1);
        postService.updatePost(1L, update);

        Post one = postService.findById(1L);


        assertThat("title update").isEqualTo(one.getTitle());
    }

    @Test
    void deletePost() {        User user1 = User.builder()
            .email("email@email.com")
            .userName("userName")
            .gender("gender")
            .password("password")
            .phoneNo("phoneNo")
            .nickName("nickName")
            .build();

        userRepository.save(user1);

        Post update = Post.builder()
                .title("title update")
                .postBody("postBody update")
                .nickName("nickName update")
                .build();
        postService.savePost(new AddDTO(1L, "title", "body", "nick"), user1);

        postService.deletePost(1L);

        List<ListDTO> all = postService.findAll();
        assertThat(all.size()).isEqualTo(0);
    }
}