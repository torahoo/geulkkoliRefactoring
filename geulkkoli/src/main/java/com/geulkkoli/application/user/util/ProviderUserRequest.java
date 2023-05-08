package com.geulkkoli.application.user.util;

import org.springframework.security.oauth2.client.registration.ClientRegistration;

public class ProviderUserRequest implements UserRequest{
    private final ClientRegistration clientRegistration;

    public ProviderUserRequest(ClientRegistration clientRegistration) {
        this.clientRegistration = clientRegistration;
    }

    public String getRegistrationId() {
        return clientRegistration.getRegistrationId();
    }


}
