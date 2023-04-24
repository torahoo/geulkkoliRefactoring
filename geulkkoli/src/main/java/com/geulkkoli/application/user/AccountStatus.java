package com.geulkkoli.application.user;

import lombok.Getter;

@Getter
public enum AccountStatus {
    ENALBE, PASSWORD_EXPIRED, LOCKED, EXPIRED, DISABLED,ACTIVE;

    private String status;

    public String getStatus() {
        return status;
    }
}
