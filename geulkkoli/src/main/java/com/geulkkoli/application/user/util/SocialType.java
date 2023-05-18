package com.geulkkoli.application.user.util;

public enum SocialType {
    KAKAO("kakao"), NAVER("naver"), GOOGLE("google");

    private String value;

    SocialType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
