package com.geulkkoli.domain.post;

import com.geulkkoli.domain.post.service.PostService;

import com.geulkkoli.web.post.dto.ListDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * TestDataInit Class 참조
 */
@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;


    @BeforeEach
    void init() {
        postService.savePost(new Post(1L, "title", "body", "nick"));
        postService.savePost(new Post(2L, "title", "body", "nick"));
        postService.savePost(new Post(3L, "title", "body", "nick"));
        postService.savePost(new Post(4L, "title", "body", "nick"));
    }

    @AfterEach
    void tearDown() {
        postService.deleteAll();
    }
    @Test
    void findById() {
        Post post = postService.findById(1L);

        assertThat("title").isEqualTo(post.getTitle());
    }

    @Test
    void findAll() {


        assertThat(postService.findAll().size()).isEqualTo(4);
    }

    @Test
    void updatePost() {
        postService.updatePost(1L, new Post(1L, "title update", "body update", "nick update"));

        Post one = postService.findById(1L);

        assertThat("title update").isEqualTo(one.getTitle());
    }

    @Test
    void deletePost() {

        postService.deletePost(1L);

        List<ListDTO> all = postService.findAll();
        assertThat(all.size()).isEqualTo(3);
    }
}