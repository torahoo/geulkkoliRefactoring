package com.geulkkoli.application.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.web.mypage.dto.edit.PasswordEditFormDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface PasswordService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    boolean isPasswordVerification(User user, PasswordEditFormDto passwordEditFormDto);

    void updatePassword(Long id, String tempPassword);

    int setLength(int startLength, int endLength);

    String createTempPassword(int length);

    String authenticationNumber(int length);

}
