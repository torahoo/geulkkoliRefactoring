package com.geulkkoli.application.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountActivityElementTest {
    @Test
    void accountActivly() {
        AccountActivityElement accountActivityElement = AccountActivityElement.builder()
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();

        assertTrue(accountActivityElement.isEnabled());
        assertTrue(accountActivityElement.isAccountNonExpired());
        assertTrue(accountActivityElement.isAccountNonLocked());
        assertTrue(accountActivityElement.isCredentialsNonExpired());
    }
}