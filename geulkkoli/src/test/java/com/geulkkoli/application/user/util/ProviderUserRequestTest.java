package com.geulkkoli.application.user.util;


import com.geulkkoli.application.security.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import static org.assertj.core.api.Assertions.assertThat;

public class ProviderUserRequestTest {

    @DisplayName("프로바이더로부터 유저정보를 받아온다.")
    @Test
    public void create() {
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId(SocialType.KAKAO.getValue())
                .authorizationGrantType(new AuthorizationGrantType(Role.USER.getRoleName()))
                .build();
        ProviderUserRequest providerUserRequest = new ProviderUserRequest(clientRegistration);
        

        assertThat(providerUserRequest.getRegistrationId()).isEqualTo(SocialType.KAKAO.getValue());
    }
}
