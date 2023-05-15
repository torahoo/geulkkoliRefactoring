package com.geulkkoli.application.user;

import com.geulkkoli.application.security.AccountStatus;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Getter
public class AuthUser implements UserDetails, OAuth2User  {
    private final String userId;
    private final String userName; //로그인 시 아이디에 해당한다. 우리 서비스의 경우 email
    private final String password;
    private final boolean isEnabled;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final Collection<GrantedAuthority> authorities;
    private final Map<String, Object> attributes;
    private String userRealName;
    private String nickName;
    private String phoneNo;
    private String gender;

    private AuthUser(UserModelDto userModel, Collection<GrantedAuthority> authorities, AccountStatus accountStatus, Map<String, Object> attributes) {
        this.userName = userModel.getEmail();
        this.password = userModel.getPassword();
        this.authorities = authorities;
        this.nickName = userModel.getNickName();
        this.userId = userModel.getUserId();
        this.gender = userModel.getGender();
        this.phoneNo = userModel.getPhoneNo();
        this.userRealName = userModel.getUserName();
        this.isAccountNonExpired = accountStatus.isAccountNonExpired();
        this.isAccountNonLocked = accountStatus.isAccountNonLocked();
        this.isCredentialsNonExpired = accountStatus.isCredentialsNonExpired();
        this.isEnabled = accountStatus.isEnabled();
        this.attributes = attributes;
    }

    public static AuthUser from(UserModelDto userModel, Collection<GrantedAuthority> authorities, AccountStatus accountStatus) {
        return new AuthUser(userModel, authorities, accountStatus, Map.of());
    }

    public static AuthUser from(UserModelDto userModel, Collection<GrantedAuthority> authorities, AccountStatus accountStatus, Map<String, Object> attributes) {
        return new AuthUser(userModel, authorities, accountStatus, attributes);
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    //사용자 계정이 만료되었는지 여부를 나타냅니다. 만료된 계정은 인증할 수 없습니다.
    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    //사용자가 잠겨 있는지 또는 잠금 해제되었는지 여부를 나타냅니다. 잠긴 사용자는 인증할 수 없습니다.
    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    //사용자의 자격증명(비밀번호)이 만료되었는지 여부를 나타냅니다. 자격 증명이 만료되면 인증이 불가능합니다.
    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    //사용자가 활성화되었는지 또는 비활성화되었는지를 나타냅니다. 비활성화된 사용자는 인증할 수 없습니다.
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getGender() {
        return gender;
    }

    public void modifyNickName(String nickName) {
        this.nickName = nickName;
    }

    public void modifyPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void modifyGender(String gender) {
        this.gender = gender;
    }

    public void modifyUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthUser)) return false;
        AuthUser authUser = (AuthUser) o;
        return Objects.equals(userName, authUser.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return userName;
    }
}
