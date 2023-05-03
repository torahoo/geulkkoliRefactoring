package com.geulkkoli.application.user;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.dto.edit.PasswordEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final UserRepository userRepository;
    private final SecureRandom random = new SecureRandom();

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";
    private static final String ALL_CHAR = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;


    @Override
    public boolean isPasswordVerification(User user, PasswordEditDto passwordEditDto) {
        return passwordEncoder.matches(passwordEditDto.getOldPassword(), user.getPassword());
    }

    @Override
    public void updatePassword(Long id, String newPassword) {
        userRepository.editPassword(id, passwordEncoder.encode(newPassword));
    }

    @Override
    public int setTempPasswordLength(int startLength, int endLength) {
        int length = 0;
        while (length < startLength)
            length = random.nextInt(endLength + 1); // nextInt 메소드 범위: 0 ~ n-1

        return length;
    }

    @Override
    public String createTempPassword(int length) {
        StringBuilder tempPassword = new StringBuilder(length);

        // 비밀번호에 영문/숫자/특문 각 하나씩 추가
        tempPassword.append(CHAR_LOWER.charAt(random.nextInt(CHAR_LOWER.length())))
                .append(NUMBER.charAt(random.nextInt(NUMBER.length())))
                .append(OTHER_CHAR.charAt(random.nextInt(OTHER_CHAR.length())));

        // 이제 나머지 비밀번호 자릿수 채워줄 랜덤값
        for (int i = 3; i < length; i++) {
            tempPassword.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }

        return tempPassword.toString();
    }

}
