package com.geulkkoli.domain.admin.service;

import com.geulkkoli.domain.admin.AccountLock;
import com.geulkkoli.domain.admin.AccountLockRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountLockRepository accountLockRepository;

    public AdminServiceImpl() {
    }

    public void rockUser(Long userId, String reason) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        AccountLock accountLock = AccountLock.of(user, reason, LocalDateTime.now().plusDays(2));
        user.rock(accountLock);
        accountLockRepository.save(accountLock);
    }

    public User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
    }
}
