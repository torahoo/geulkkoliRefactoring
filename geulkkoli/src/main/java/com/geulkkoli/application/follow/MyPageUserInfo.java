package com.geulkkoli.application.follow;

import java.time.LocalDateTime;

public class MyPageUserInfo {
    private final String nickName;
    private final String createdAt;

    private MyPageUserInfo(String nickName, String createdAt) {
        this.nickName = nickName;
        this.createdAt= createdAt;
    }

    public static MyPageUserInfo of(String nickName, String createdAt) {
        return new MyPageUserInfo(nickName,createdAt);
    }

    public String getNickName() {
        return nickName;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
