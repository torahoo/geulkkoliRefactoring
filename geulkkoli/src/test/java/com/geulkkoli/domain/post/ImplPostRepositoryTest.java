package com.geulkkoli.domain.post;

import com.geulkkoli.domain.post.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
@Transactional
class ImplPostRepositoryTest {

    @Autowired
    private PostRepository implPostRepository;

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

        Post update = new Post(1L, "title02", "body02", "nick02");
        implPostRepository.update(1L, update);
        Optional<Post> one = implPostRepository.findById(1L);
        Assertions.assertThat(one.get().getTitle()).isEqualTo(update.getTitle());
    }

    @Test
    void delete() {
        implPostRepository.delete(1L);
        List<Post> all = implPostRepository.findAll();

        Assertions.assertThat(all.size()).isEqualTo(3);
    }
}