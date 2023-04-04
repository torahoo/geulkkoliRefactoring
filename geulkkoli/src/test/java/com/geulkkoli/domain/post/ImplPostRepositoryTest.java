package com.geulkkoli.domain.post;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class ImplPostRepositoryTest {

    @Autowired
    private ImplPostRepository implPostRepository;
//    @AfterEach
//    public void

    @Test
    void save() {
        Post post = new Post("title", "body", "nick");
        Post save = implPostRepository.save(post);
        Assertions.assertThat(save).isEqualTo(post);
    }

    @Test
    void findById() {
        Post post = new Post("title", "body", "nick");
        Post save = implPostRepository.save(post);
        Optional<Post> find = implPostRepository.findById(save.getPostNo());
        Assertions.assertThat(save).isEqualTo(find.get());
    }

    @Test
    void findAll() {
        List<Post> all = implPostRepository.findAll();
        Assertions.assertThat(3).isEqualTo(all.size());
    }

    @Test
    void update() {
//        Post save = implPostRepository.save(new Post("title01", "body01", "nick01"));
        Post update = new Post("title02", "body02", "nick02");
        implPostRepository.update(1L, update);
        Optional<Post> one = implPostRepository.findById(1L);
        Assertions.assertThat(one.get().getTitle()).isEqualTo(update.getTitle());
    }

    @Test
    void delete() {

        Post post1 = implPostRepository.save(new Post("title01", "body01", "nick01"));
        Post post2 = implPostRepository.save(new Post("title02", "body02", "nick02"));

        implPostRepository.delete(post1.getPostNo());
        List<Post> all = implPostRepository.findAll();

        Assertions.assertThat(all.size()).isEqualTo(1);
    }
}