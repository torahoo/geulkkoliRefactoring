package com.geulkkoli.domain.post.repository;

import com.geulkkoli.domain.post.Post;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
@Transactional
@ActiveProfiles("test")
public class PostRepositoryUnitTest {

    @Autowired
    private TestPostRepository testPostRepository;



//    @BeforeAll
//    static void beforAll() {
//        log.info("testPostRepository.isNull={}", Objects.isNull(testPostRepository));
//
//        for (int i = 0; i < 100; ++i) {
//            testPostRepository.save(new Post("Test " + 1, "Test Data " + i, "seungin"));
//        }
//    }

    @Test
    void save() {
        Post post = testPostRepository.save(new Post("Test title", "Test body", "Tester"));
        log.info("post save={}", post.toString());
        assertThat(post.getNickName()).isEqualTo("Tester");
    }

    @Test
    void findAllTwo() {
        //given
        int page = 0;
        int size = 10;
        int totalElements = 100;
        for (int i = 0; i < totalElements; ++i) {
            testPostRepository.save(new Post("Test " + i, "Test", "Tester" + 1));
        }

        //when
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Post> result = testPostRepository.findAll(pageRequest);

        //then
        assertThat(totalElements / size).isEqualTo(result.getTotalPages());

    }
}
