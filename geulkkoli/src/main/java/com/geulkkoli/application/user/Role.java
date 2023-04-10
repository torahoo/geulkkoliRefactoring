package com.geulkkoli.application.user;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    Role(String roleName) {
        this.roleName = roleName;
    }

    private String roleName;
}
