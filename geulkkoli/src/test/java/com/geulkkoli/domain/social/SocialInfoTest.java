package com.geulkkoli.domain.social;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SocialInfoTest {

    @Test
    void getSocialType() {
        SocialInfo socialInfo = SocialInfo.builder()
                .socialId("kakao")
                .socialType("kakao")
                .socialConnectDate(LocalDateTime.of(2021, 5, 23, 0, 0).toString())
                .build();

        assertEquals("kakao", socialInfo.getSocialType());
    }
}