package com.geulkkoli.domain.social;

import com.geulkkoli.application.social.util.SocialType;
import com.geulkkoli.web.mypage.ConnectedSocialInfos;
import com.geulkkoli.web.social.SocialInfoDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Boolean existsBySocialTypeAndSocialId(String socialType, String socialId) {
        return socialInfoRepository.existsBySocialTypeAndSocialId(socialType, socialId);
    }

    @Transactional(readOnly = true)
    public ConnectedSocialInfos findConnectedInfos(String email) {
        List<SocialInfo> allByUserEmail = findAllByUser_eamil(email);
        if (allByUserEmail.isEmpty()) {
            return new ConnectedSocialInfos(List.of());
        }
        return new ConnectedSocialInfos(allByUserEmail);
    }
    @Transactional
    public void disconnect(String email, String socialType) {
     socialInfoRepository.findSocialInfoBySocialTypeAndAndUser_Email(socialType, email).ifPresent(socialInfoRepository::delete);
    }

    private List<SocialInfo> findAllByUser_eamil(String email) {
        List<SocialInfo> allByUserEmail = socialInfoRepository.findAllByUser_Email(email);
        return allByUserEmail;
    }


}
