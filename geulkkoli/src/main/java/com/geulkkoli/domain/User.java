package com.geulkkoli.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor

public class User {

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
}
