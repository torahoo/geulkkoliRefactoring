package com.geulkkoli.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {
    @Test
    void getUserName() {
        User user = User.builder()
                .userId("kkk")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build();

        assertThat(user.getUserName()).isEqualTo("김");
    }

    @Test
    @DisplayName("비밀번호가 일치하는 지 확인한다.")
    void matchPassword() {
        User user = User.builder()
                .userId("kkk")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build();

        assertTrue(user.matchPassword("1234"));
    }
}