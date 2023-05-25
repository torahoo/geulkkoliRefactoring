package com.geulkkoli.domain.social;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SocialInfoService {
    private final SocialInfoRepository socialInfoRepository;


    public SocialInfoService(SocialInfoRepository socialInfoRepository) {
        this.socialInfoRepository = socialInfoRepository;
    }

    @Transactional
    public SocialInfo save(SocialInfo socialInfo){
        return socialInfoRepository.save(socialInfo);
    }

    @Transactional(readOnly = true)
    public SocialInfo findBySocialTypeAndSocialId(String socialType, String socialId){
        return socialInfoRepository.findSocialInfoBySocialTypeAndSocialType(socialType,socialId).orElseThrow(()->new IllegalArgumentException("해당하는 소셜 정보가 없습니다."));
    }

    @Transactional
    public void delete(SocialInfo socialInfo){
        socialInfoRepository.delete(socialInfo);
    }

    @Transactional
    public Boolean existsBySocialTypeAndSocialId(String socialType, String socialId){
        return socialInfoRepository.existsBySocialTypeAndSocialId(socialType,socialId);
    }
}
