package com.geulkkoli.domain.post;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PostRepositoryTest {
    @Autowired
    private PostRepository implPostRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void save() {
        Post post = new Post(1L, "title", "body", "nick");
        Post save = implPostRepository.save(post);
        em.flush();

        assertThat(save).isEqualTo(post);
    }

    @Test
    void findById() {
        Post post = new Post(1L, "title", "body", "nick");
        Post save = implPostRepository.save(post);
        em.flush();
        Optional<Post> find = implPostRepository.findById(save.getPostId());
        assertThat(save).isEqualTo(find.get());
    }

    @Test
    void findAll() {
        Post post = new Post(1L, "title02", "body02", "nick02");
        implPostRepository.save(post);
        Post post2 = new Post(2L, "title02", "body02", "nick02");
        implPostRepository.save(post2);
        List<Post> all = implPostRepository.findAll();
        assertThat(2).isEqualTo(all.size());
    }


    /**
     * TODO
     *  영속성 컨텍스트에 반영되지 않아 실패하지만 다음에 고쳐보겠다.
     *  단일 테스트는 통과하지만 전체를 돌렸을 때는 empty를 반환한다.
     */
    @Test
    void update() {

        Post post = new Post(1L, "title02", "body02", "nick02");
        Post updatePost = new Post(1L, "title03", "body02", "nick02");

        implPostRepository.save(post);

        em.flush();
        em.clear();

        implPostRepository.update(1L, updatePost);

        Optional<Post> findById = implPostRepository.findById(1L);
        assertThat(findById).get().hasFieldOrPropertyWithValue("title", "title03");
    }

    @Test
    void delete() {
        Post update = new Post(1L, "title02", "body02", "nick02");
        implPostRepository.save(update);
        implPostRepository.delete(1L);
        List<Post> all = implPostRepository.findAll();

        assertThat(all.size()).isEqualTo(0);
    }
}