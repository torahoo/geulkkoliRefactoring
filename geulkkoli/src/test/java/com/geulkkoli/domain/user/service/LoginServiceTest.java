package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.domain.user.service.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    LoginService loginService;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository.save( User.builder()
                .email("tako1@naver.com")
                .userName("김1")
                .nickName("바나나1")
                .password("123412")
                .phoneNo("01012345671")
                .gender("male")
                .build());
    }

    @Test
    @DisplayName("로그인테스트")
    void loginTest() {
        //given
        User exitsUser = User.builder()
                .email("tako1@naver.com")
                .userName("김1")
                .nickName("바나나1")
                .password("123412")
                .phoneNo("01012345671")
                .gender("male")
                .build();
        //when
        Optional<User> loginUser = loginService.login("tako1@naver.com", "123412");

        //then
        assertAll(() -> assertThat(loginUser).isPresent(),
                () -> assertThat(loginUser).get().hasFieldOrPropertyWithValue("email","tako1@naver.com"));
    }

    @Test
    @DisplayName("로그인실패시_널을 반환한다.")
    void throwErrorWhenLoginFailedTest() {
        //given
        Optional<User> noneExistentUser = loginService.login("tako@naver.com", "123412");
        //when

        //then
        assertThat(noneExistentUser).isEmpty();
    }

}
