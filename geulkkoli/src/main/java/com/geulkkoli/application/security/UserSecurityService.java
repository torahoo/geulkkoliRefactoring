package com.geulkkoli.application.security;

import com.geulkkoli.application.user.AuthUser;
import com.geulkkoli.application.user.Role;
import com.geulkkoli.application.user.UserModelDto;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.domain.user.UserRepositoryVer2;
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

    private final UserRepositoryVer2 userRepository;

    public UserSecurityService(UserRepositoryVer2 userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> findByEmailUser = userRepository.findByEmail(email);
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
        UserModelDto userModel = UserModelDto.toDto(user);
        AuthUser authenticatedUser = new AuthUser(userModel, authorities);
        authenticatedUser.setEnabled(true);
        authenticatedUser.setAccountNonExpired(true);
        authenticatedUser.setAccountNonLocked(true);
        authenticatedUser.setCredentialsNonExpired(true);
        return authenticatedUser;
    }



}
