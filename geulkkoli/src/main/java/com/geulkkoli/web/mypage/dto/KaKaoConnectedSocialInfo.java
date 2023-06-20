package com.geulkkoli.web.mypage.dto;

import com.geulkkoli.web.mypage.dto.ConnectSocialInfo;

public class KaKaoConnectedSocialInfo implements ConnectSocialInfo {
    private String socialType;
    private Boolean connected;

    public KaKaoConnectedSocialInfo(String socialType, Boolean connected) {
        this.socialType = socialType;
        this.connected = connected;
    }

    public String getSocialType() {
        return socialType;
    }

    public Boolean isConnect() {
        return connected;
    }
}
