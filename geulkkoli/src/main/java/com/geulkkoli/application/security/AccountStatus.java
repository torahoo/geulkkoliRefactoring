package com.geulkkoli.application.security;

import lombok.Getter;

@Getter
public enum AccountStatus {
    PASSWORD_EXPIRED(true,true,true,false),
    LOCKED(true,true,false,true),
    ACCOUNT_EXPIRED(true,false,true,true),
    DISABLED(false,true,true,true),
    ACTIVE(true,true,true,true);

    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    AccountStatus(boolean isEnabled, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired) {
        this.isEnabled = isEnabled;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

}
