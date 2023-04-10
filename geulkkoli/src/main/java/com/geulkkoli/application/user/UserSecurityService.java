package com.geulkkoli.application.user;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.LoginService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserSecurityService implements UserDetailsService {

    private final LoginService loginService;

    public UserSecurityService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> findByEmailUser = loginService.login(email,"1234");
        if (findByEmailUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        User user = findByEmailUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getEmail().contains("admin")) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getRoleName()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getRoleName()));
        }
        AuthenticatedUser authenticatedUser = AuthenticatedUser.of(user, authorities);
        authenticatedUser.setEnabled(true);
        authenticatedUser.setAccountNonExpired(true);
        authenticatedUser.setAccountNonLocked(true);
        authenticatedUser.setCredentialsNonExpired(true);
        return authenticatedUser;
    }
}
