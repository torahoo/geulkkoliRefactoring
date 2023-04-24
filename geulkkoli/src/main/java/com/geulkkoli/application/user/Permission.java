package com.geulkkoli.application.user;

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
    @OneToMany(mappedBy = "permission")
    private Set<User> users = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Embedded
    private AccountActivityElement accountActivityElement;

    public Permission() {
    }

    private Permission(AccountActivityElement accountActivityElement, AccountStatus accountStatus) {
        this.accountActivityElement = accountActivityElement;
        this.accountStatus = accountStatus;
    }

    public static Permission of(AccountActivityElement accountActivityElement, AccountStatus accountStatus) {
        return new Permission(accountActivityElement, accountStatus);
    }

    public AccountActivityElement getAccountActivityElement() {
        return accountActivityElement;
    }


    public void changeAccountActivityElement(AccountActivityElement accountActivityElement) {
        this.accountActivityElement = accountActivityElement;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.addPermission(this);
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
}
