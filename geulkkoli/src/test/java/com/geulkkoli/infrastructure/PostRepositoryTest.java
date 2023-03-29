package com.geulkkoli.infrastructure;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void save() {
        Post post = Post.builder()
                .nickName("나")
                .postBody("나나")
                .title("테스트").build();

        Post savePost = postRepository.save(post);

        assertThat(savePost).isEqualTo(post);
    }

    @Test
    void findById() {
        Post post = Post.builder()
                .nickName("나")
                .postBody("나나")
                .title("테스트").build();

        postRepository.save(post);

        Post findPost = postRepository.findById(1L)
                .orElse(null);

        assertThat(findPost).isEqualTo(post);
    }
}