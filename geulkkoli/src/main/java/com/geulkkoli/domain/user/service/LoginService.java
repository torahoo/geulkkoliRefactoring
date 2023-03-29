package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    /**
     * @return null 로그인 실패
     */
    @Transactional(readOnly = true)
    public User login(String loginId, String password) {
        return userRepository.findByUserId(loginId)
                .filter(m -> m.matchPassword(password))
                .orElseThrow(() ->new LoginFailureException("존재하지 않는 사용자 입니다."));
    }
}
