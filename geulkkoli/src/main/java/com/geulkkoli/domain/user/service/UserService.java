package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.JoinFormDto;
import com.geulkkoli.web.user.edit.EditFormDto;
import com.geulkkoli.web.user.edit.EditPasswordFormDto;
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

    public void join(JoinFormDto form) {
        userRepository.save(form.toEntity(passwordEncoder));
    }
    public void update(Long id, EditFormDto editFormDto) {
        userRepository.update(id, editFormDto);
    }

    // 현재 비밀번호 입력 시 기존 비밀번호와 일치하는지 확인
    public boolean isPasswordVerification(Long id, EditPasswordFormDto editPasswordFormDto) {
        return passwordEncoder.matches(findById(id).getPassword(), editPasswordFormDto.getPassword());
    }

    public void updatePassword(Long id, EditPasswordFormDto editPasswordFormDto) {
        userRepository.updatePassword(id, passwordEncoder.encode(editPasswordFormDto.getNewPassword()));
    }

    public void delete(User user) {
        userRepository.deleteById(user.getUserId());
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user found id matches:" + id));
    }
}
