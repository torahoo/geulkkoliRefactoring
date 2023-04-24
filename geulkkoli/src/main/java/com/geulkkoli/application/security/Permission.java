package com.geulkkoli.application.security;

import com.geulkkoli.domain.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "permission",fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;

    public Permission() {
    }

    private Permission(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
        this.isAccountNonExpired = accountStatus.isAccountNonExpired();
        this.isCredentialsNonExpired = accountStatus.isCredentialsNonExpired();
        this.isEnabled = accountStatus.isEnabled();
        this.isAccountNonLocked = accountStatus.isAccountNonLocked();
    }

    public static Permission of(AccountStatus accountStatus) {
        return new Permission(accountStatus);
    }

    public Set<User> getUsers() {
        return users;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }


    public void addUser(User user) {
        this.users.add(user);
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
}
