package com.geulkkoli.domain.post;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@DataJpaTest
@SpringBootTest
class ImplPostRepositoryTest {

    @Autowired
    private PostRepository implPostRepository;


    @Test
    @Transactional
    void save() {
        Post post = new Post("title", "body", "nick");
        Post save = implPostRepository.save(post);
        Optional<Post> find = implPostRepository.findById(save.getPostId());
        Assertions.assertThat(find.get()).isEqualTo(post);
    }

    @Test
    @Transactional
    void findById() {
        Post post = new Post("title", "body", "nick");
        Post save = implPostRepository.save(post);
        Optional<Post> find = implPostRepository.findById(save.getPostId());
        Assertions.assertThat(save).isEqualTo(find.get());
    }

    @Test
    @Transactional
    void findAll() {
        Post post = new Post("title", "body", "nick");
        Post save = implPostRepository.save(post);
        Optional<Post> find = implPostRepository.findById(save.getPostId());
        List<Post> all = implPostRepository.findAll();
        Assertions.assertThat(1).isEqualTo(all.size());
    }

    @Test
    @Transactional
    void update() {
        Post post = new Post("title", "body", "nick");
        Post save = implPostRepository.save(post);
        Post update = new Post("title02", "body02", "nick02");
        implPostRepository.update(1L, update);
        Optional<Post> one = implPostRepository.findById(1L);
        Assertions.assertThat(one.get().getTitle()).isEqualTo(update.getTitle());
    }

    @Test
    @Transactional
    void delete() {
        Post post = new Post("title", "body", "nick");
        Post save = implPostRepository.save(post);
        implPostRepository.delete(1L);
        List<Post> all = implPostRepository.findAll();

        Assertions.assertThat(all.size()).isEqualTo(0);
    }
}