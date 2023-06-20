package com.geulkkoli.application.follow;

public class MyPageUserInfo {
    private final String nickName;

    private MyPageUserInfo(String nickName) {
        this.nickName = nickName;
    }

    public static MyPageUserInfo of(String nickName) {
        return new MyPageUserInfo(nickName);
    }

    public String getNickName() {
        return nickName;
    }
}
