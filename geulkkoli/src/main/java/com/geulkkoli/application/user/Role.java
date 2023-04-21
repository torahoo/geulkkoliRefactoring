package com.geulkkoli.application.user;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ROLE_ADMIN", 1),
    USER("ROLE_USER", 2);

    private String roleName;
    private int roleCode;

    Role(String roleName, int roleCode) {
        this.roleName = roleName;
        this.roleCode = roleCode;
    }

}
