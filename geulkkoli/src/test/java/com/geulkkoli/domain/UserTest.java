
package com.geulkkoli.domain;

import com.geulkkoli.domain.user.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UserTest {
    @Test
    void getUserName() {
        User user = User.builder()
                .email("tako@naver.com")
                .userName("김")
                .nickName("바나나")
                .password("12345")
                .phoneNo("01012345678")
                .build();

        assertThat(user.getUserName()).isEqualTo("김");
    }


}