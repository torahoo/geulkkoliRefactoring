package com.geulkkoli.domain.post;

import com.geulkkoli.domain.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void savePost() {
    }

    @Test
    void updatePost() {
//        Post onePost = postService.findById(1L);
//        log.info("onePost={}", onePost);
//        Post update = new Post("title02", "body02", onePost.getNickName());
        log.info("@Test ::1 one={}", postService.findById(1L));
        postService.updatePost(1L, new Post("title update", "body update", "nick update"));
        Post one = postService.findById(1L);
        log.info("@Test ::2 one={}", one);
        Assertions.assertThat("title update").isEqualTo(one.getTitle());
    }
}