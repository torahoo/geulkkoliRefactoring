package com.geulkkoli.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    void getUserName() {
        Users user = Users.builder()
                .userId("kkk")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build();

        assertThat(user.getUserName()).isEqualTo("김");
    }
}