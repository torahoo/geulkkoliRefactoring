package com.geulkkoli.domain.user;

import com.geulkkoli.domain.admin.AccountLock;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void isLock() {

        User user = User.builder()
                .email("email@gmail.com")
                .userName("userName")
                .password("password")
                .nickName("nickName")
                .phoneNo("0102221111")
                .gender("male")
                .build();
        AccountLock accountLock = AccountLock.of(user, "reason", LocalDateTime.now().plusDays(7));
        user.rock(accountLock);

        assertTrue(user.isLock());
    }
}