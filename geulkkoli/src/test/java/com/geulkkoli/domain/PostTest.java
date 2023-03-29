package com.geulkkoli.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PostTest {
    @Test
    void getPost() {
        Post post = Post.builder()
                .nickName("나")
                .postBody("나나")
                .title("테스트").build();

        assertAll(() -> assertThat(post.getTitle()).isEqualTo("테스트"),
                () -> assertThat(post.getPostBody()).isEqualTo("나나"),
                () -> assertThat(post.getNickName()).isEqualTo("나"));
    }
}