package com.geulkkoli.domain.user;

import com.geulkkoli.application.security.LockExpiredTimeException;
import com.geulkkoli.domain.admin.AccountLock;
import com.geulkkoli.domain.admin.Report;
import com.geulkkoli.domain.post.Post;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("계정 잠금 테스트")
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

    @Test
    @DisplayName("계정 잠금 시간이 설정이 되어있지 않다면 LockExpiredTimeException을 던진다")
    void if_no_lock_expiration_time_is_throw_LockedExpiredTimeException() {

        User user = User.builder()
                .email("email@gmail.com")
                .userName("userName")
                .password("password")
                .nickName("nickName")
                .phoneNo("0102221111")
                .gender("male")
                .build();
        AccountLock accountLock = AccountLock.of(user, "reason", null);
        user.rock(accountLock);

        assertThrows(LockExpiredTimeException.class, user::isLock);
    }

    @Test
    void addReport() {
        User user = User.builder()
                .email("email@gmail.com")
                .userName("userName")
                .password("password")
                .nickName("nickName")
                .phoneNo("0102221111")
                .gender("male")
                .build();
        Post post = Post.builder()
                .nickName("nickName")
                .title("title")
                .postBody("content")
                .build();
        Set<Report> reports = user.getReports();

        Report reason = user.writeReport(post, "reason");

        assertThat(user).has(new Condition<>(u -> u.getReports().contains(reason), "report가 추가되었다"));
    }

    @Test
    void deleteReport() {
    }
}