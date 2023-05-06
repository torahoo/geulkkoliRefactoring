package com.geulkkoli.application.security;

import com.geulkkoli.application.user.AuthUser;
import com.geulkkoli.application.user.PasswordService;
import com.geulkkoli.application.user.UserModelDto;
import com.geulkkoli.domain.admin.AccountLockRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.dto.JoinFormDto;
import lombok.RequiredArgsConstructor;
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

import static java.lang.Boolean.TRUE;

@Service
@Transactional
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
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
        if (user.getRole().getRole().equals(Role.ADMIN)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getRoleName()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getRoleName()));
        }
    }

    public User join(JoinFormDto form) {
        User user = form.toEntity(passwordService.passwordEncoder);

        RoleEntity roleEntity = user.hasRole(Role.USER);
        roleRepository.save(roleEntity);
        userRepository.save(user);
        return user;
    }


    /*
     * 관리자 실험을 위한 임시 관리자 계정 추가용 메서드*/
    public void joinAdmin(JoinFormDto form) {
        User user = form.toEntity(passwordService.passwordEncoder);
        RoleEntity roleEntity = user.hasRole(Role.ADMIN);
        roleRepository.save(roleEntity);
        userRepository.save(user);
    }

}