package com.geulkkoli.domain.user;

import com.geulkkoli.application.security.AccountStatus;
import com.geulkkoli.application.security.Permission;
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

        Permission permission = Permission.of(AccountStatus.ACTIVE);
        user.addPermission(permission);

        Permission rockPermission = Permission.of( AccountStatus.LOCKED);
        user.rock(rockPermission);


        assertFalse(user.getPermission().isAccountNonLocked());
    }
}