package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.JoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean isEmailDuplicate(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isNickNameDuplicate(String nickName) {
        return userRepository.findByNickName(nickName).isPresent();
    }

    public boolean isPhoneNoDuplicate(String phoneNo) {
        return userRepository.findByPhoneNo(phoneNo).isPresent();
    }

    public void join(JoinForm form) {
        userRepository.save(form.toEntity(passwordEncoder));
    }

    public void delete(User user) {
        userRepository.delete(user.getUserId());
    }

    @Transactional(readOnly = true)
    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(m -> passwordEncoder.matches(password, m.getPassword()));
    }


}
