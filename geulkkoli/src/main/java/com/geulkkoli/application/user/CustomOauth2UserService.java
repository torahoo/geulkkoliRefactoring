package com.geulkkoli.application.user;

import com.geulkkoli.application.security.AccountStatus;
import com.geulkkoli.application.user.util.KaKaoRequestConverter;
import com.geulkkoli.application.user.util.ProviderUserRequest;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class CustomOauth2UserService extends AbstractOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        ProviderUserRequest providerUserRequest = new ProviderUserRequest(userRequest.getClientRegistration(), oAuth2User);
        KaKaoRequestConverter kaKaoRequestConverter = new KaKaoRequestConverter();
        ProviderUser kakaoUser = kaKaoRequestConverter.convert(providerUserRequest);

        User user = join(kakaoUser);

        UserModelDto build = UserModelDto.builder()
                .userId(user.getUserId())
                .gender(user.getGender())
                .userName(user.getUserName())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .password(user.getPassword())
                .phoneNo(user.getPhoneNo()).build();

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) kakaoUser.getAuthorities();
        return AuthUser.from(build, authorities, AccountStatus.ACTIVE, kakaoUser.getAttributes());
    }
}
