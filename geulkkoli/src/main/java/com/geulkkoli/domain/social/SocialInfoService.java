package com.geulkkoli.domain.social;

import com.geulkkoli.web.myPage.ConnectedSocialInfos;
import com.geulkkoli.web.social.SocialInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SocialInfoService {
    private final SocialInfoRepository socialInfoRepository;


    public SocialInfoService(SocialInfoRepository socialInfoRepository) {
        this.socialInfoRepository = socialInfoRepository;
    }

    @Transactional
    public SocialInfo save(SocialInfoDto socialInfo) {
        return socialInfoRepository.save(socialInfo.toEntity());
    }

    @Transactional(readOnly = true)
    public SocialInfo findBySocialTypeAndSocialId(String socialType, String socialId) {
        return socialInfoRepository.findSocialInfoBySocialTypeAndSocialId(socialType, socialId).orElseThrow(() -> new IllegalArgumentException("해당하는 소셜 정보가 없습니다."));
    }

    @Transactional
    public void delete(SocialInfo socialInfo) {
        socialInfoRepository.delete(socialInfo);
    }

    @Transactional(readOnly = true)
    public Boolean isAssociatedRecord(String socialType, String socialId) {
        return socialInfoRepository.existsBySocialTypeAndSocialId(socialType, socialId);
    }

    @Transactional(readOnly = true)
    public Boolean isConnected(String socialId) {
        Optional<SocialInfo> socialInfo = socialInfoRepository.findSocialInfoBySocialId(socialId);
        if (socialInfo.isEmpty()) {
            return true;
        }
        log.info("socialInfo.get().getConnected() : {}", socialInfo.get().getConnected());
        return socialInfo.get().getConnected();
    }

    @Transactional(readOnly = true)
    public ConnectedSocialInfos findConnectedInfos(String email) {
        List<SocialInfo> allByUserEmail = findAllByUser_email(email);
        if (allByUserEmail.isEmpty()) {
            return new ConnectedSocialInfos(List.of());
        }
        return new ConnectedSocialInfos(allByUserEmail);
    }

    @Transactional
    public void disconnect(String email, String socialType) {
        socialInfoRepository.findSocialInfoBySocialTypeAndAndUser_Email(socialType, email).ifPresent(socialInfo -> {
            socialInfo.disconnect();
            socialInfoRepository.save(socialInfo);
        });
    }

    @Transactional(readOnly = true)
    public List<SocialInfo> findAllByUser_email(String email) {
        return socialInfoRepository.findAllByUser_Email(email);
    }


    public void save(SocialInfo socialInfo) {
        socialInfoRepository.save(socialInfo);
    }
}
