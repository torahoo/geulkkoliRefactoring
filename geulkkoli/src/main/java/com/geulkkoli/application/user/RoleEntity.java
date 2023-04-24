package com.geulkkoli.application.user;

import com.geulkkoli.application.user.util.RoleNameAttributeConverter;
import com.geulkkoli.domain.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Convert(converter = RoleNameAttributeConverter.class)
    private Role role;
    @OneToMany(mappedBy = "role")
    private Set<User> users = new HashSet<>();

    public RoleEntity() {
    }

    private RoleEntity(Role role) {
        this.role = role;
    }

    public static RoleEntity of(Role role ) {
        return new RoleEntity(role);
    }

    public Long getRoleId() {
        return roleId;
    }

    public Role getRole() {
        return role;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.addRole(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleEntity)) return false;
        RoleEntity that = (RoleEntity) o;
        return role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }
}
