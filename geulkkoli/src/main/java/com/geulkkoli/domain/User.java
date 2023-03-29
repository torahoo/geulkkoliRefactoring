package com.geulkkoli.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    private String userId;
    private String userName;

    private String password;
    private String nickName;

    @Builder
    public User(String userId, String userName, String password, String nickName) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
    }

    public boolean matchPassword(String password) {
        return password.matches(this.password);
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getUserNo() {
        return userNo;
    }

    public String getNickName() {
        return nickName;
    }
}
