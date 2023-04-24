package com.geulkkoli.application.user;

import com.geulkkoli.application.user.util.RoleNameAttributeConverter;
import com.geulkkoli.domain.user.User;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Convert(converter = RoleNameAttributeConverter.class)
    private Role role;
    @Embedded
    private AccountActivityElement accountActivityElement;

    public Permission() {
    }

    private Permission(User user, Role roleName, AccountActivityElement accountActivityElement) {
        this.user = user;
        this.role = roleName;
        this.accountActivityElement = accountActivityElement;
    }

    public static Permission of(User user, Role role, AccountActivityElement accountActivityElement) {
        return new Permission(user, role, accountActivityElement);
    }


    public Role getRole() {
        return role;
    }

    public AccountActivityElement getAccountActivityElement() {
        return accountActivityElement;
    }


    public void changeAccountActivityElement(AccountActivityElement accountActivityElement) {
        this.accountActivityElement = accountActivityElement;
    }

}
