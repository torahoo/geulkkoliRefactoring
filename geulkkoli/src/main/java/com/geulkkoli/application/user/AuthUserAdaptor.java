package com.geulkkoli.application.user;

import com.geulkkoli.domain.user.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthUserAdaptor extends CustomUserDetails {
    private User user;

    private Collection<? extends GrantedAuthority> authorities;

    public AuthUserAdaptor(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public AuthUserAdaptor(User user, Collection<? extends GrantedAuthority> authorities, User user1, Collection<? extends GrantedAuthority> authorities1) {
        super(user, authorities);
        this.user = user1;
        this.authorities = authorities1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }


    public String nickName(){
        return this.user.getNickName();
    }
    public String userName(){
        return this.user.getUserName();
    }
    public String phoneNo(){
        return this.user.getPhoneNo();
    }
    public Long userId(){
        return this.user.getUserId();
    }
    public String gender(){
        return this.user.getGender();
    }
}
