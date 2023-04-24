package com.geulkkoli.application.user;

import com.geulkkoli.application.security.AccountStatus;
import com.geulkkoli.application.security.Permission;
import com.geulkkoli.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PermissionTest {


    @Test
    @DisplayName("권한 생성 테스트")
    void permission() {
        User user = User.builder()
                .email("tako@naver1.com")
                .userName("김1")
                .nickName("바나나1")
                .password(("12341"))
                .gender("male")
                .phoneNo("01012345679")
                .build();

        Permission permission = Permission.of(AccountStatus.ACTIVE);
        permission.addUser(user);

        assertAll(() -> {
            assertThat(permission.isEnabled()).isTrue();
            assertThat(permission.isAccountNonLocked()).isTrue();
            assertThat(permission.isCredentialsNonExpired()).isTrue();
            assertThat(permission.isAccountNonExpired()).isTrue();
        });
    }

    @Test
    @DisplayName("계정 활성화 변경 테스트")
    void accountActivityElement() {
        User user = User.builder()
                .email("tako@naver1.com")
                .userName("김1")
                .nickName("바나나1")
                .password(("12341"))
                .gender("male")
                .phoneNo("01012345679")
                .build();

        Permission permission = Permission.of(AccountStatus.DISABLED);
        permission.addUser(user);

        assertAll(() -> {
            assertThat(permission.isEnabled()).isFalse();
            assertThat(permission.isAccountNonLocked()).isTrue();
            assertThat(permission.isCredentialsNonExpired()).isTrue();
            assertThat(permission.isAccountNonExpired()).isTrue();
        });
    }

}