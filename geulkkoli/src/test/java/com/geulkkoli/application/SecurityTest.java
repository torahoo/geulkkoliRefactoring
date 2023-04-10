package com.geulkkoli.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;

class SecurityTest {
    @Test
    void test() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = encoder.encode("mypassword");
        assertThat(encoder.matches("mypassword",result)).isTrue();
    }
}
