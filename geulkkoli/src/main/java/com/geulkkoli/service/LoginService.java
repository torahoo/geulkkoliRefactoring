package com.geulkkoli.service;


import com.geulkkoli.domain.Users;
import com.geulkkoli.respository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UsersRepository usersRepository;
    /**
     * @return null 로그인 실패
     */
    public Users login(String loginId, String password) {
        return usersRepository.findByUserId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElseThrow(() ->new LoginFailureException("존재하지 않는 사용자 입니다."));
    }
}
