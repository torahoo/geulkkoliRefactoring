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

    public void join(JoinFormDto form) {
        userRepository.save(form.toEntity(passwordEncoder));
    }

//    @Transactional(readOnly = true)
//    public Optional<User> login(String email, String password) {
//        return userRepository.findByEmail(email)
//                .filter(m -> m.matchPassword(password));
//    }

    public Optional<User> update(Long id, EditFormDto form) {
        userRepository.update(id, form);
        return userRepository.findById(id);
    }

    public boolean isPasswordVerification(Long id, EditPasswordFormDto form) {
        return passwordEncoder.matches(form.getPassword(), findById(id).getPassword());
    }

    public void updatePassword(Long id, EditPasswordFormDto form) {
        userRepository.updatePassword(id, passwordEncoder.encode(form.getNewPassword()));
    }

    public void delete(User user) {
        userRepository.delete(user.getUserId());
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user found id matches:" + id));
    }

}
