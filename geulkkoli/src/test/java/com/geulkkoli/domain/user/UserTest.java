package com.geulkkoli.domain.user;

import com.geulkkoli.application.user.AccountActivityElement;
import com.geulkkoli.application.user.AccountStatus;
import com.geulkkoli.application.user.Permission;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;


class UserTest {

    @Test
    @DisplayName("회원 계정을 잠근다.")
    void rock() {
        User user = User.builder()
                .email("tako@gmail.com")
                .password("1234")
                .phoneNo("010-1234-5678")
                .gender("M").build();

        AccountActivityElement element = AccountActivityElement.builder()
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .isEnabled(true)
                .build();

        Permission permission = Permission.of(element, AccountStatus.ACTIVE);
        permission.addUser(user);

        AccountActivityElement element2 = AccountActivityElement.builder()
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(false)
                .isEnabled(true)
                .build();
        user.rock(element2);

        assertFalse(user.getPermission().getAccountActivityElement().isAccountNonLocked());
    }
}