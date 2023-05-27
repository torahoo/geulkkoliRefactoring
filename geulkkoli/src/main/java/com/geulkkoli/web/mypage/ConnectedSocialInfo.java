package com.geulkkoli.web.mypage;

public class ConnectedSocialInfo {
    private String clientregistrationName;

    public ConnectedSocialInfo( String clientregistrationName) {
        this.clientregistrationName = clientregistrationName;
    }

    public String getSocialType() {
        return clientregistrationName;
    }

    public Boolean isConnect(String SocialType) {
        return clientregistrationName.equals(SocialType);
    }
}
