package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.edit.UserInfoEditDto;
import com.geulkkoli.web.user.edit.PasswordEditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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

    public void edit(Long id, UserInfoEditDto userInfoEditDto) {
        userRepository.edit(id, userInfoEditDto);
    }

    // 현재 비밀번호 입력 시 기존 비밀번호와 일치하는지 확인
    public boolean isPasswordVerification(Long id, PasswordEditDto passwordEditDto) {
        return passwordEncoder.matches(findById(id).getPassword(), passwordEditDto.getOldPassword());
    }

    public void editPassword(Long id, PasswordEditDto passwordEditDto) {
        userRepository.editPassword(id, passwordEncoder.encode(passwordEditDto.getNewPassword()));
    }

    public void delete(User user) {
        userRepository.deleteById(user.getUserId());
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user found id matches:" + id));
    }
}
