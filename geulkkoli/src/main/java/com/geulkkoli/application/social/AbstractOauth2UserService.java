package com.geulkkoli.application.social;

import com.geulkkoli.domain.social.SocialInfo;
import com.geulkkoli.domain.social.SocialInfoService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserFindService;
import com.geulkkoli.web.social.SocialInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j

@Service
@RequiredArgsConstructor
public abstract class AbstractOauth2UserService {
    private final UserFindService userFindService;

    private final SocialInfoService socialInfoService;

    @Transactional(readOnly = true)
    public Boolean isSignUp(String email) {
        Optional<User> signUpUser = userFindService.findByEmail(email);
        return signUpUser.isPresent();
    }

    @Transactional(readOnly = true)
    public User userInfo(String email) {
        return userFindService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void connect(String socialId, String socialType, User user) {
        SocialInfoDto socialInfoDto = new SocialInfoDto(socialId, socialType, user);
        socialInfoService.connect(socialInfoDto);
    }

    public Boolean haveAssociatedRecord(String socialId, String socialType) {
        return socialInfoService.isAssociatedRecord(socialType, socialId);
    }

    public Boolean isConnected(String socialId) {
        return socialInfoService.isConnected(socialId);
    }

    public Boolean reConnected(String socialId, String socialType) {
        SocialInfo socialInfo = socialInfoService.findBySocialTypeAndSocialId(socialType, socialId);
        socialInfo.reConnected(true);
        socialInfoService.reconnect(socialInfo);
        return true;
    }

    @Transactional(readOnly = true)
    public User findUserBySocialId(String socialId, String socialType) {
        return socialInfoService.findBySocialTypeAndSocialId(socialType, socialId).getUser();
    }
}
