package com.geulkkoli.application.social;

import com.geulkkoli.application.security.AccountStatus;
import com.geulkkoli.application.security.Role;
import com.geulkkoli.application.user.CustomAuthenticationPrinciple;
import com.geulkkoli.application.user.ProviderUser;
import com.geulkkoli.application.user.UserModelDto;
import com.geulkkoli.application.social.util.DelegateOAuth2RequestConverter;
import com.geulkkoli.application.social.util.ProviderUserRequest;
import com.geulkkoli.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.*;

@Slf4j
@Transactional
@Service
public class CustomOauth2UserService extends AbstractOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        ProviderUserRequest providerUserRequest = new ProviderUserRequest(userRequest.getClientRegistration(), oAuth2User);
        DelegateOAuth2RequestConverter delegateOAuth2RequestConverter = new DelegateOAuth2RequestConverter();
        ProviderUser providerUser = delegateOAuth2RequestConverter.convert(providerUserRequest);
        List<GrantedAuthority> authorities = new ArrayList<>();

        boolean isSignUp = TRUE.equals(isSignUp(providerUser));
        boolean isConnected = TRUE.equals(isConnected(providerUser));
        if (isSignUp && isConnected) {
            log.info("providerUser : {}", providerUser.getId());
            User user = userInfo(providerUser);
            UserModelDto model = singedUpUserToModel(user);
            authorities.add(new SimpleGrantedAuthority(user.roleName()));
            return CustomAuthenticationPrinciple.from(model, authorities, AccountStatus.ACTIVE, providerUser.getAttributes());
        }

        if (!isSignUp && !isConnected){
            UserModelDto model = provideUserToModel(providerUser);
            authorities.add(new SimpleGrantedAuthority(Role.GUEST.getRoleName()));
            return CustomAuthenticationPrinciple.from(model, authorities, AccountStatus.ACTIVE, providerUser.getAttributes());
        }

        throw new OAuth2AuthenticationException("이미 가입한 이메일입니다. 이메일로 로그인 후 연동해주세요.");
    }

    private UserModelDto provideUserToModel(ProviderUser providerUser) {

        return UserModelDto.builder()
                .userId(providerUser.getId())
                .gender(providerUser.getGender())
                .userName(providerUser.getUsername())
                .email(providerUser.getEmail())
                .nickName(providerUser.getNickName())
                .password(providerUser.getPassword())
                .phoneNo("0100000000").build();

    }

    private UserModelDto singedUpUserToModel(User user) {
        return UserModelDto.toDto(user);
    }
}
