package com.geulkkoli.application.user;

import com.geulkkoli.application.security.AccountStatus;
import com.geulkkoli.application.user.util.DelegateOAuth2RequestConverter;
import com.geulkkoli.application.user.util.ProviderUserRequest;
import com.geulkkoli.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@Slf4j
public class CustomOauth2UserService extends AbstractOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        ProviderUserRequest providerUserRequest = new ProviderUserRequest(userRequest.getClientRegistration(), oAuth2User);
        DelegateOAuth2RequestConverter delegateOAuth2RequestConverter = new DelegateOAuth2RequestConverter();
        ProviderUser providerUser = delegateOAuth2RequestConverter.convert(providerUserRequest);

        User user = join(providerUser);

        UserModelDto model = convertModel(user);
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) providerUser.getAuthorities();
        return AuthUser.from(model, authorities, AccountStatus.ACTIVE, providerUser.getAttributes());
    }

    private  UserModelDto convertModel(User user) {
        return UserModelDto.builder()
                .userId(user.getUserId())
                .gender(user.getGender())
                .userName(user.getUserName())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .password(user.getPassword())
                .phoneNo(user.getPhoneNo()).build();
    }
}
