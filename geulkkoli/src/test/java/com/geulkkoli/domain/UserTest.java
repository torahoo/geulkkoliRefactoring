package com.geulkkoli.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    void getUserName() {
        User user = new User(1l,"j");

        assertThat(user.getUserName()).isEqualTo("j");
    }
}