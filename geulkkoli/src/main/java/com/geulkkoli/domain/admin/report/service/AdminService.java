package com.geulkkoli.domain.admin.report.service;

import com.geulkkoli.application.user.AccountActivityElement;
import com.geulkkoli.application.user.PermissionRepository;
import com.geulkkoli.domain.admin.report.ReportRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {
    private PermissionRepository permissionRepository;
    private  UserRepository userRepository;

    public AdminService() {
    }

    public AdminService(PermissionRepository permissionRepository, UserRepository userRepository) {
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
    }



    public void rockUser(Long userId) {


        userRepository.findById(userId).ifPresent(user -> {
            userRepository.save(user);
        });
    }

    public User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
    }
}
