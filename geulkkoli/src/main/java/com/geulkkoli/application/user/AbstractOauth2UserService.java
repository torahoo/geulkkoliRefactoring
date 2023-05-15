package com.geulkkoli.application.user;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j

@Service
public abstract class AbstractOauth2UserService {
    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public Boolean isJoin(ProviderUser providerUser) {
        Optional<User> signUpUser = userService.findByEmail(providerUser.getEmail());
        return signUpUser.isPresent();
    }

    @Transactional(readOnly = true)
    public User userInfo(ProviderUser providerUser) {
        return userService.findByEmail(providerUser.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
