package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.dto.edit.UserInfoEditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // password Encoder를 사용하는 곳을 usersecurityservice로 옮겼기 때문에 이 메서드는 필요 없어져 지웁니다.

    public void delete(User user) {
        userRepository.deleteById(user.getUserId());
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user found id matches:" + id));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUserNameAndPhoneNo(String userName, String phoneNo) {
        return userRepository.findByUserNameAndPhoneNo(userName, phoneNo);
    }

    public Optional<User> findByEmailAndUserNameAndPhoneNo(String email, String userName, String phoneNo) {
        return userRepository.findByEmailAndUserNameAndPhoneNo(email, userName, phoneNo);
    }

    public User findByNickName(String nickName) {
        return userRepository.findByNickName(nickName)
                .orElseThrow(() -> new NoSuchElementException("No user found nickname matches:" + nickName));
    }
}
