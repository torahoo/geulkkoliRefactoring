package com.geulkkoli.application.user;

import com.geulkkoli.application.security.*;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.dto.JoinFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static java.lang.Boolean.*;
import static java.lang.Boolean.TRUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUserByUsername : {}", email);
        Optional<User> findByEmailUser = userRepository.findByEmail(email);
        if (findByEmailUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        User user = findByEmailUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorizeRole(authorities, user);

        if (authorities.isEmpty()) {
            throw new RoleException("권한을 찾을 수 없습니다.");
        }

        UserModelDto userModel = UserModelDto.toDto(user);

        if (TRUE.equals(user.isLock())) {
            return AuthUser.from(userModel, authorities, AccountStatus.LOCKED);
        }
        return AuthUser.from(userModel, authorities, AccountStatus.ACTIVE);
    }


    private void authorizeRole(List<GrantedAuthority> authorities, User user) {
        if (TRUE.equals(user.getRole().isAdmin())) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getRoleName()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getRoleName()));
        }
    }

    @Transactional
    public User signUp(JoinFormDto form) {
        User user = form.toEntity(PasswordService.passwordEncoder);

        RoleEntity roleEntity = user.Role(Role.USER);
        roleRepository.save(roleEntity);
        userRepository.save(user);
        return user;
    }


    /*
     * 관리자 실험을 위한 임시 관리자 계정 추가용 메서드*/
    @Transactional
    public void signUpAdmin(JoinFormDto form) {
        User user = form.toEntity(passwordService.passwordEncoder);
        RoleEntity roleEntity = user.Role(Role.ADMIN);
        roleRepository.save(roleEntity);
        userRepository.save(user);
    }

}