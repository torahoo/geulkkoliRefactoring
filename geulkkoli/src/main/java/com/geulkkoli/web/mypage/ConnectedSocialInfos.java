package com.geulkkoli.web.mypage;

import com.geulkkoli.application.social.util.SocialType;
import com.geulkkoli.domain.social.SocialInfo;

import java.util.List;
import java.util.stream.Collectors;

public class ConnectedSocialInfos {
    List<ConnectedSocialInfo> connectedSocialInfos;

    public ConnectedSocialInfos(List<SocialInfo> socialInfos) {
        this.connectedSocialInfos = socialInfos.stream()
                .map(i -> new ConnectedSocialInfo(i.getSocialType()))
                .collect(Collectors.toList());
    }

    public Boolean isKakaoConnected() {
        return connectedSocialInfos.stream()
                .anyMatch(connectedSocialInfo -> connectedSocialInfo.isConnect(SocialType.KAKAO.getValue()));
    }

    public Boolean isNaverConnected() {
        return connectedSocialInfos.stream()
                .anyMatch(connectedSocialInfo -> connectedSocialInfo.isConnect(SocialType.NAVER.getValue()));
    }

    public Boolean isGoogleConnected() {
        return connectedSocialInfos.stream()
                .anyMatch(connectedSocialInfo -> connectedSocialInfo.isConnect(SocialType.GOOGLE.getValue()));
    }
}
