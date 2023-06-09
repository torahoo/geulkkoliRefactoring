package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserFindService {
    private final UserRepository userRepository;

    public UserFindService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user found id matches:" + id));
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("No user found email matches:" + email));
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUserNameAndPhoneNo(String userName, String phoneNo) {
        return userRepository.findByUserNameAndPhoneNo(userName, phoneNo);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmailAndUserNameAndPhoneNo(String email, String userName, String phoneNo) {
        return userRepository.findByEmailAndUserNameAndPhoneNo(email, userName, phoneNo);
    }
}
