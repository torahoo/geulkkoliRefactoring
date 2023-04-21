package com.geulkkoli.application.user;

import com.geulkkoli.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PermissionTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("tako@naver1.com")
                .userName("김1")
                .nickName("바나나1")
                .password(("12341"))
                .gender("male")
                .phoneNo("01012345679")
                .build();

    }

    @Test
    @DisplayName("권한 생성 테스트")
    void permission() {
        AccountActivityElement element = AccountActivityElement.builder()
                .isEnabled(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isAccountNonExpired(true)
                .build();

        Permission permission = Permission.of(user, Role.ADMIN, element);

        assertAll(() -> {
            assertThat(permission.getRole()).isEqualTo(Role.ADMIN);
            assertThat(permission.getAccountActivityElement().isEnabled()).isTrue();
            assertThat(permission.getAccountActivityElement().isAccountNonLocked()).isTrue();
            assertThat(permission.getAccountActivityElement().isCredentialsNonExpired()).isTrue();
            assertThat(permission.getAccountActivityElement().isAccountNonExpired()).isTrue();
        });
    }

    @Test
    @DisplayName("계정 활성화 변경 테스트")
    void accountActivityElement() {
        AccountActivityElement element = AccountActivityElement.builder()
                .isEnabled(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isAccountNonExpired(true)
                .build();

        Permission permission = Permission.of(user, Role.USER, element);

        AccountActivityElement newElement = AccountActivityElement.builder()
                .isEnabled(false)
                .isAccountNonLocked(false)
                .isCredentialsNonExpired(false)
                .isAccountNonExpired(false)
                .build();

        permission.changeAccountActivityElement(newElement);

        assertAll(() -> {
            assertThat(permission.getAccountActivityElement().isEnabled()).isFalse();
            assertThat(permission.getAccountActivityElement().isAccountNonLocked()).isFalse();
            assertThat(permission.getAccountActivityElement().isCredentialsNonExpired()).isFalse();
            assertThat(permission.getAccountActivityElement().isAccountNonExpired()).isFalse();
        });
    }

}