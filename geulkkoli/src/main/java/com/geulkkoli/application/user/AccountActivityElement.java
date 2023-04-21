package com.geulkkoli.application.user;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
@Getter
@Embeddable
public class AccountActivityElement {
    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;

    public AccountActivityElement() {
    }

    @Builder
    public AccountActivityElement(boolean isEnabled, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired) {
        this.isEnabled = isEnabled;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }


}
