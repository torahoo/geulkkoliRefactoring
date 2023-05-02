package com.geulkkoli.application.user;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.web.user.dto.edit.PasswordEditDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface PasswordService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    boolean isPasswordVerification(User user, PasswordEditDto passwordEditDto);

    void updatePassword(Long id, String tempPassword);

    int setTempPasswordLength(int startLength, int endLength);

    String createTempPassword(int length);

}
