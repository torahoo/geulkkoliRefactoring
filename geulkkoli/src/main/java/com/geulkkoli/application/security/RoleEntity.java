package com.geulkkoli.application.security;

import com.geulkkoli.application.user.util.RoleNameAttributeConverter;
import com.geulkkoli.domain.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by hoogokok on 2023/4/21
 * Role Entity
 * Role Entity는 Role Enum 타입을 DB에 저장하기 위한 엔티티 입니다.
 * Role Entity는 User Entity와 일대다 연관관계를 가집니다.
 * User Entity는 Role Entity와 다대일 연관관계를 가집니다.
 */
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleEntity)) return false;
        RoleEntity that = (RoleEntity) o;
        return Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }
}
