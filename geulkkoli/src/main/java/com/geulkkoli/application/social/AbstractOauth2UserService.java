package com.geulkkoli.application.social;

import com.geulkkoli.domain.social.SocialInfo;
import com.geulkkoli.domain.social.SocialInfoService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.social.SocialInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j

@Service
public abstract class AbstractOauth2UserService {
    @Autowired
    private UserService userService;

    @Autowired
    private SocialInfoService socialInfoService;

    @Transactional(readOnly = true)
    public Boolean isSignUp(String email) {
        Optional<User> signUpUser = userService.findByEmail(email);
        return signUpUser.isPresent();
    }


    @Transactional(readOnly = true)
    public User userInfo(String email) {
        return userService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void connect(String socialId, String socialType, User user) {
        SocialInfoDto socialInfoDto = new SocialInfoDto(socialId, socialType, user);
        socialInfoService.save(socialInfoDto);
    }

    @Transactional(readOnly = true)
    public Boolean haveAssociatedRecord(String socialId, String socialType) {
        return socialInfoService.isAssociatedRecord(socialType, socialId);
    }

    @Transactional(readOnly = true)
    public Boolean isConnected(String socialId) {
        return socialInfoService.isConnected(socialId);
    }

    @Transactional
    public Boolean reConnected(String socialId, String socialType) {
        SocialInfo socialInfo = socialInfoService.findBySocialTypeAndSocialId(socialType, socialId);
        socialInfo.reConnected(true);
        socialInfoService.save(socialInfo);
        return true;
    }

    @Transactional(readOnly = true)
    public User findUserBySocialId(String socialId, String socialType) {
        return socialInfoService.findBySocialTypeAndSocialId(socialType, socialId).getUser();
    }
}
