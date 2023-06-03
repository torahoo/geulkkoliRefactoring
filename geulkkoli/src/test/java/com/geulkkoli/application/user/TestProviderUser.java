package com.geulkkoli.application.user;

import com.geulkkoli.application.social.OAuth2ProviderUser;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class TestProviderUser extends OAuth2ProviderUser {
    private final Map<String, Object> attributes;

    public TestProviderUser(Map<String, Object> attributes, OAuth2User oAuth2User, ClientRegistration clientRegistration, Map<String, Object> attributes1) {
        super(attributes, oAuth2User, clientRegistration);
        this.attributes = attributes1;
    }

    @Override
    public String getId() {
        return getClientRegistration().getRegistrationId();
    }

    @Override
    public String getUsername() {
        return getOAuth2User().getName();
    }

    @Override
    public String getNickName() {
        return attributes.get("nickName").toString();
    }
}
