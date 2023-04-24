package com.geulkkoli.application.security;

import com.geulkkoli.application.user.*;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.JoinFormDto;
import com.geulkkoli.web.user.edit.PasswordEditDto;
import org.springframework.security.core.AuthenticationException;
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

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserSecurityService(UserRepository userRepository, PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> findByEmailUser = userRepository.findByEmail(email);
        if (findByEmailUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        User user = findByEmailUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        Permission permission = user.getPermission();
        authorizeRule(authorities, user);
        if (authorities.isEmpty()){
            throw new PermissException("권한 정보를 찾을 수  없습니다.");
        }

        AccountActivityElement activityElement = permission.getAccountActivityElement();
        UserModelDto userModel = UserModelDto.toDto(user);
        AuthUser authenticatedUser = new AuthUser(userModel, authorities);

        addEnableElement(activityElement, authenticatedUser);

        return authenticatedUser;
    }

    private void addEnableElement(AccountActivityElement activityElement, AuthUser authenticatedUser) {
        authenticatedUser.setEnabled(activityElement.isEnabled());
        authenticatedUser.setAccountNonExpired(activityElement.isAccountNonExpired());
        authenticatedUser.setAccountNonLocked(activityElement.isAccountNonLocked());
        authenticatedUser.setCredentialsNonExpired(activityElement.isCredentialsNonExpired());
    }

    private List<GrantedAuthority> authorizeRule(List<GrantedAuthority> authorities, User user){
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
        RoleEntity roleEntity = RoleEntity.of(Role.USER);
        roleEntity.addUser(user);
        roleRepository.save(roleEntity);

        AccountActivityElement element = accountActivity();
        Permission permission = Permission.of(element, AccountStatus.ACTIVE);
        permission.addUser(user);

        Permission userPermission = permissionRepository.save(permission);
        user.addPermission(userPermission);
        userRepository.save(user);
        return user;
    }

    private AccountActivityElement accountActivity() {
        return AccountActivityElement.builder().isAccountNonLocked(true).isAccountNonExpired(true).isCredentialsNonExpired(true).isEnabled(true).build();
    }

    public boolean isPasswordVerification(User user, PasswordEditDto passwordEditDto) {
        return passwordEncoder.matches(passwordEditDto.getOldPassword(), user.getPassword());
    }

    public void updatePassword(Long id, PasswordEditDto passwordEditDto) {
        userRepository.editPassword(id, passwordEncoder.encode(passwordEditDto.getNewPassword()));
    }
}
