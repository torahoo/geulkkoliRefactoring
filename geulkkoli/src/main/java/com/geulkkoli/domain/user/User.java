package com.geulkkoli.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    private String userName;

    @Getter(AccessLevel.NONE)
    private String password;
    private String nickName;

    private String email;
    private Integer phoneNo;
    private String sex;

    @Builder
    public User(String userName, String password, String nickName, String email, Integer phoneNo, String sex) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.sex = sex;
    }

    public boolean matchPassword(String password) {
        return password.matches(this.password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(userNo, user.userNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNo);
    }
}
