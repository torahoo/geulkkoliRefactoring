package com.geulkkoli.application.user.util;

import com.geulkkoli.application.user.ProviderUser;
import com.geulkkoli.application.user.UserRequestConverter;
import com.geulkkoli.application.user.social.KakaoOAuth2User;

import java.util.Map;

public class KaKaoRequestConverter implements UserRequestConverter<ProviderUserRequest, ProviderUser>{
    @Override
    public ProviderUser convert(ProviderUserRequest providerUserRequest) {
        return new KakaoOAuth2User(getAttributes(providerUserRequest), providerUserRequest.getOAuth2User(), providerUserRequest.getClientRegistration());
    }

    private Map<String, Object> getAttributes(ProviderUserRequest providerUserRequest) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) providerUserRequest.getOAuth2User().getAttributes().get("kakao_account");
        return kakaoAccount;
    }
}
