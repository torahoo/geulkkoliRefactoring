package com.geulkkoli.web.social;

public class SocialSignUpDto {
    private final String nickName;
    private final String password;
    private final String phoneNumber;

    public static class Builder {
        private String nickName;
        private String password;
        private String phoneNumber;

        public Builder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public SocialSignUpDto build() {
            return new SocialSignUpDto(this);
        }
    }

    private SocialSignUpDto(Builder builder) {
        this.nickName = builder.nickName;
        this.password = builder.password;
        this.phoneNumber = builder.phoneNumber;
    }


    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
