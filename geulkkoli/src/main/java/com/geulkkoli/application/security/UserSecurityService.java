package com.geulkkoli.application.security;

import com.geulkkoli.application.user.*;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.JoinFormDto;
import com.geulkkoli.web.user.edit.EditPasswordFormDto;
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
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserSecurityService(UserRepository userRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> findByEmailUser = userRepository.findByEmail(email);
        if (findByEmailUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        User user = findByEmailUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        Permission permission = permissionRepository.findByUser(user);

        if (permission.getRole().equals(Role.ADMIN)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getRoleName()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getRoleName()));
        }

        AccountActivityElement activityElement = permission.getAccountActivityElement();
        UserModelDto userModel = UserModelDto.toDto(user);

        AuthUser authenticatedUser = new AuthUser(userModel, authorities);
        authenticatedUser.setEnabled(activityElement.isEnabled());
        authenticatedUser.setAccountNonExpired(activityElement.isAccountNonExpired());
        authenticatedUser.setAccountNonLocked(activityElement.isAccountNonLocked());
        authenticatedUser.setCredentialsNonExpired(activityElement.isCredentialsNonExpired());

        return authenticatedUser;
    }

    public void join(JoinFormDto form) {
        User user = userRepository.save(form.toEntity(passwordEncoder));
        AccountActivityElement element = accountActivity();
        Permission permission = Permission.of(user, Role.USER, element);
        permissionRepository.save(permission);
    }

    private AccountActivityElement accountActivity() {
        return AccountActivityElement.builder().isAccountNonLocked(true).isAccountNonExpired(true).isCredentialsNonExpired(true).isEnabled(true).build();
    }

    public boolean isPasswordVerification(User user, EditPasswordFormDto editPasswordFormDto) {
        return passwordEncoder.matches(user.getPassword(), editPasswordFormDto.getPassword());
    }

    public void updatePassword(Long id, EditPasswordFormDto editPasswordFormDto) {
        userRepository.updatePassword(id, passwordEncoder.encode(editPasswordFormDto.getNewPassword()));
    }
}
