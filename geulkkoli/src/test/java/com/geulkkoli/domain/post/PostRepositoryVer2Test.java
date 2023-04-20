package com.geulkkoli.domain.post;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Transactional
@ActiveProfiles("test")
class PostRepositoryVer2Test {

    @Autowired
    private PostRepositoryVer2 implPostRepository;



    @AfterEach
    void afterEach () {
        implPostRepository.deleteAll();
    }

    @BeforeEach
    void beforeEach () {
        implPostRepository.save(Post.builder()
                .authorId(1L)
                .nickName("바나나")
                .postBody("나는 멋지고 섹시한 개발자")//채&훈
                .title("여러분").build()
        );
        implPostRepository.save(Post.builder()
                .authorId(2L)
                .title("testTitle01")
                .postBody("test postbody 01")
                .nickName("점심뭐먹지").build()
        );
        implPostRepository.save(Post.builder()
                .authorId(2L)
                .title("testTitle02")
                .postBody("test postbody 02")
                .nickName("점심뭐먹지").build()
        );
        implPostRepository.save(Post.builder()
                .authorId(2L)
                .title("testTitle03")
                .postBody("test postbody 03")
                .nickName("점심뭐먹지").build()
        );

    }

    @Test
    void save() {
        Post post = new Post(1L, "title", "body", "nick");
        Post save = implPostRepository.save(post);
        assertThat(save).isEqualTo(post);
    }

    @Test
    void findById() {
        Post post = new Post(1L, "title", "body", "nick");
        Post save = implPostRepository.save(post);
        Optional<Post> find = implPostRepository.findById(save.getPostId());
        assertThat(save).isEqualTo(find.get());
    }

    @Test
    void findAll() {
        List<Post> all = implPostRepository.findAll();
        assertThat(4).isEqualTo(all.size());
    }

    @Test
    void update() {
        Post savePost = implPostRepository.save(new Post(1L, "new01", "newBody01", "newNick01"));
        savePost.setTitle("update");
        savePost.setPostBody("updateBody");
        implPostRepository.save(savePost);
        Optional<Post> one = implPostRepository.findById(savePost.getPostId());
        assertThat(one).hasValue(savePost);
    }

    @Test
    void delete() {

        implPostRepository.deleteById(1L);
        List<Post> all = implPostRepository.findAll();

        assertThat(all.size()).isEqualTo(3);
    }
}