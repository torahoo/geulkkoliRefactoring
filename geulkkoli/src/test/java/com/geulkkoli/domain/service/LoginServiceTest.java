package com.geulkkoli.domain.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.domain.user.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class LoginServiceTest {

    @Autowired
    LoginService loginService;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository.save(User.builder()
                .email("tako@naver.com")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build());
    }

    @Test
    @DisplayName("로그인테스트")
    void loginTest() {
        //given
        User exitsUser = User.builder()
                .email("tako@naver.com")
                .password("1234")
                .build();
        //when
        Optional<User> loginUser = loginService.login("tako@naver.com", "1234");

        //then
        assertThat(loginUser)
                .isNotEmpty()
                .hasValue(exitsUser);
    }

    @Test
    @DisplayName("로그인실패시_널을 반환한다.")
    void throwErrorWhenLoginFailedTest() {
        //given
        Optional<User> noneExistentUser = loginService.login("tako@naver.com", "1243");
        //when

        //then
        assertThat(noneExistentUser).isEmpty();
    }

}
