package com.geulkkoli.application.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthUser extends User {
    private final Long userId;
    private final String userName;
    private final String nickName;
    private final String phoneNo;

    private final String gender;
    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private Collection<GrantedAuthority> authorities;

    public AuthUser(UserModelDto userModel, Collection<GrantedAuthority> authorities) {
        super(userModel.getEmail(), userModel.getPassword(), authorities);
        this.authorities = authorities;
        this.nickName = userModel.getNickName();
        this.userId = userModel.getUserId();
        this.gender = userModel.getGender();
        this.phoneNo = userModel.getPhoneNo();
        this.userName = userModel.getUserName();
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
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
}
