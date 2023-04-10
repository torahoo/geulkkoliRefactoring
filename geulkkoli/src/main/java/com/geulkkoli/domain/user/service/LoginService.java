package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * @return null 로그인 실패
     */
    @Transactional(readOnly = true)
    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(m -> m.matchPassword(password, passwordEncoder));
    }
}
