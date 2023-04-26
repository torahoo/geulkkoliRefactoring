package com.geulkkoli.domain.post;

import com.geulkkoli.domain.post.entity.Post;
import com.geulkkoli.domain.post.service.PostService;

import com.geulkkoli.web.post.dto.ListDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TestDataInit Class 참조
 */
@SpringBootTest
@Slf4j
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

//    @Test
//    void findById() {
//        Post post = postService.findById(1L);
//        Assertions.assertThat("여러분").isEqualTo(post.getTitle());
//    }
//
//    @Test
//    void findAll() {
//        List<ListDTO> all = postService.findAll();
//        Assertions.assertThat(all.size()).isEqualTo(4);
//    }
//
//    @Test
//    void savePost() {
//        Post post = Post.builder().authorId(1L).title("Test").postBody("test code").nickName("Tester").build();
//        Long postId = postService.savePost(post);
//        Assertions.assertThat(postId).isEqualTo(5);
//    }
//
//    @Test
//    void updatePost() {
////        Post onePost = postService.findById(1L);
////        log.info("onePost={}", onePost);
////        Post update = new Post("title02", "body02", onePost.getNickName());
//        log.info("@Test ::1 one={}", postService.findById(1L));
//        postService.updatePost(1L, new Post(1L, "title update", "body update", "nick update"));
//        Post one = postService.findById(1L);
//        log.info("@Test ::2 one={}", one);
//        Assertions.assertThat("title update").isEqualTo(one.getTitle());
//    }
//
//    @Test
//    void DeletePost() {
//        postService.deletePost(1L);
//        List<ListDTO> all = postService.findAll();
//        Assertions.assertThat(all.size()).isEqualTo(3);
//    }
}