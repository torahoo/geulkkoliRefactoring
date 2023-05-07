package com.geulkkoli.application.user;

import com.geulkkoli.application.security.AccountStatus;
import com.geulkkoli.application.security.Role;
import com.geulkkoli.application.security.UserSecurityService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Autowired
    private UserRepository userRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest : {}", userRequest.getClientRegistration());
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
        String email = (String) kakaoAccount.get("email");
        Optional<User> singUpUser = userRepository.findByEmail(email);
        Object gender = kakaoAccount.get("gender");
        if (singUpUser.isEmpty()) {
            log.info("회원가입");
            Map<String, Object> proifile = (Map<String, Object>) kakaoAccount.get("profile");
            String randomUUID = UUID.randomUUID().toString();
            String dummpyPassword = email + randomUUID;
            if (Objects.isNull(gender)) {
                gender = "none";
            }
            User user = User.builder()
                    .userName((String) proifile.get("nickname"))
                    .email(email)
                    .nickName("kakao" + (String) proifile.get("nickname"))
                    .password(passwordEncoder.encode(dummpyPassword))
                    .gender(gender.toString())
                    .phoneNo("010-0000-0000")
                    .build();
            userRepository.save(user);
        }
        Optional<User> byEmail = userRepository.findByEmail(email);
        Map<String, Object> proifile = (Map<String, Object>) kakaoAccount.get("profile");
        log.info("email : {}", email);
        log.info("nickname : {}", proifile.get("nickname"));
        log.info("gender : {}", gender);
        UserModelDto build = UserModelDto.builder()
                .userId(byEmail.get().getUserId())
                .gender(byEmail.get().getGender())
                .userName(byEmail.get().getUserName())
                .email(email)
                .nickName(byEmail.get().getNickName())
                .password("1234")
                .phoneNo("010-0000-0000").build();


        List<GrantedAuthority> authorities = oAuth2User.getAuthorities().stream()
                .map(grantedAuthority -> new SimpleGrantedAuthority(grantedAuthority.getAuthority()))
                .collect(Collectors.toList());

        return AuthUser.from(build, authorities, AccountStatus.ACTIVE, oAuth2User.getAttributes());
    }
}
