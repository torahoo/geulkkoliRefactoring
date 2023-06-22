package com.geulkkoli.domain.social;

import com.geulkkoli.web.social.SocialInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@Service
public class SocialInfoService {
    private final SocialInfoRepository socialInfoRepository;

    public SocialInfoService(SocialInfoRepository socialInfoRepository) {
        this.socialInfoRepository = socialInfoRepository;
    }

    public SocialInfo connect(SocialInfoDto socialInfo) {
        return socialInfoRepository.save(socialInfo.toEntity());
    }

    public SocialInfo reconnect(SocialInfo socialInfo) {
        return socialInfoRepository.save(socialInfo);
    }

    public void delete(SocialInfo socialInfo) {
        socialInfoRepository.delete(socialInfo);
    }

    public void disconnect(String email, String socialType) {
        socialInfoRepository.findSocialInfoBySocialTypeAndAndUser_Email(socialType, email).ifPresent(socialInfo -> {
            socialInfo.disconnect();
            socialInfoRepository.save(socialInfo);
        });
    }

}
