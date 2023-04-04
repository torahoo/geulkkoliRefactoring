package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JoinService {

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

    public void join(User user) {
        userRepository.save(user);
    }


}
