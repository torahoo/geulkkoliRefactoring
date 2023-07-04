package com.geulkkoli.domain.social.service;

import com.geulkkoli.domain.social.SocialInfo;
import com.geulkkoli.domain.social.SocialInfoRepository;
import com.geulkkoli.web.user.dto.mypage.ConnectedSocialInfos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class SocialInfoFindService {
    private final SocialInfoRepository socialInfoRepository;

    public SocialInfo findBySocialTypeAndSocialId(String socialType, String socialId) {
        return socialInfoRepository.findSocialInfoBySocialTypeAndSocialId(socialType, socialId).orElseThrow(() -> new IllegalArgumentException("해당하는 소셜 정보가 없습니다."));
    }


    public Boolean isAssociatedRecord(String socialType, String socialId) {
        return socialInfoRepository.existsBySocialTypeAndSocialId(socialType, socialId);
    }

    public Boolean isConnected(String socialId) {
        Optional<SocialInfo> socialInfo = socialInfoRepository.findSocialInfoBySocialId(socialId);
        if (socialInfo.isEmpty()) {
            return true;
        }
        log.info("socialInfo.get().getConnected() : {}", socialInfo.get().getConnected());
        return socialInfo.get().getConnected();
    }

    public ConnectedSocialInfos findConnectedInfos(String email) {
        List<SocialInfo> allByUserEmail = findAllByUser_eamil(email);
        if (allByUserEmail.isEmpty()) {
            return new ConnectedSocialInfos(List.of());
        }
        return new ConnectedSocialInfos(allByUserEmail);
    }

    public List<SocialInfo> findAllByUser_eamil(String email) {
        return socialInfoRepository.findAllByUser_Email(email);
    }

    public  ConnectedSocialInfos  findAllByNickName(String nickName) {
        List<SocialInfo> allByUserNickName = socialInfoRepository.findAllByUserNickName(nickName);
        if (allByUserNickName.isEmpty()) {
            return new ConnectedSocialInfos(List.of());
        }
        return new ConnectedSocialInfos(allByUserNickName);
    }

}
