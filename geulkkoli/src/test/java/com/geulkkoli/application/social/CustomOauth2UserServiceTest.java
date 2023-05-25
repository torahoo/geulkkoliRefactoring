package com.geulkkoli.application.social;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class CustomOauth2UserServiceTest {

    private CustomOauth2UserService customOauth2UserService = new CustomOauth2UserService();

    @Test
    @DisplayName("가입된 이메일이지만 소셜 연동이 되어있지 않을 경우")
    void if_existEmail_but_not_connected_social_info() {

    }
}