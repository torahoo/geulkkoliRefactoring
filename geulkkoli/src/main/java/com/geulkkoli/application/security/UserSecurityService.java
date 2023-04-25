package com.geulkkoli.application.security;

import com.geulkkoli.application.user.*;
import com.geulkkoli.domain.admin.AccountLockRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.JoinFormDto;
import com.geulkkoli.web.user.edit.PasswordEditDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final AccountLockRepository accountLockRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserSecurityService(UserRepository userRepository, AccountLockRepository accountRockRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.accountLockRepository = accountRockRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> findByEmailUser = userRepository.findByEmail(email);
        if (findByEmailUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        User user = findByEmailUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorizeRule(authorities, user);

        if (authorities.isEmpty()) {
            throw new RoleException("권한을 찾을 수 없습니다.");
        }

        UserModelDto userModel = UserModelDto.toDto(user);

        if (Boolean.TRUE.equals(accountLockRepository.existsByLockedUser_UserId(user.getUserId()))) {
            return AuthUser.from(userModel, authorities, AccountStatus.LOCKED);
        }
        return AuthUser.from(userModel, authorities, AccountStatus.ACTIVE);
    }

    private List<GrantedAuthority> authorizeRule(List<GrantedAuthority> authorities, User user) {
        if (user.getRole().getRole().equals(Role.ADMIN)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getRoleName()));
            return authorities;
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getRoleName()));
            return authorities;
        }
    }

    public User join(JoinFormDto form) {
        User user = form.toEntity(passwordEncoder);

        RoleEntity roleEntity = roleRepository.save(RoleEntity.of(Role.USER));
        user.addRole(roleEntity);
        userRepository.save(user);
        return user;
    }

    public boolean isPasswordVerification(User user, PasswordEditDto passwordEditDto) {
        return passwordEncoder.matches(passwordEditDto.getOldPassword(), user.getPassword());
    }

    public void updatePassword(Long id, PasswordEditDto passwordEditDto) {
        userRepository.editPassword(id, passwordEncoder.encode(passwordEditDto.getNewPassword()));
    }
}
