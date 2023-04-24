package com.geulkkoli.domain.admin.service;

import com.geulkkoli.application.security.AccountStatus;
import com.geulkkoli.application.security.Permission;
import com.geulkkoli.application.security.PermissionRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private UserRepository userRepository;

    public AdminServiceImpl() {
    }

    public void rockUser(Long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            Permission rockPermission = rock();
            Permission save = permissionRepository.save(rockPermission);
            user.rock(save);
            userRepository.save(user);
        });

    }

    private Permission rock() {
        Permission rockPermission = Permission.of(AccountStatus.LOCKED);
        return rockPermission;
    }

    public User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
    }
}
