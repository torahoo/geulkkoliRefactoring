package com.geulkkoli.application.user;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountActivityElement)) return false;
        AccountActivityElement that = (AccountActivityElement) o;
        return isEnabled == that.isEnabled && isAccountNonExpired == that.isAccountNonExpired && isAccountNonLocked == that.isAccountNonLocked && isCredentialsNonExpired == that.isCredentialsNonExpired;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isEnabled, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired);
    }
}
