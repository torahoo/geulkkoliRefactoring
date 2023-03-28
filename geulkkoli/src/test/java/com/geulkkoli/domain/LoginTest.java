package com.geulkkoli.domain;

import com.geulkkoli.respository.UsersRepository;
import com.geulkkoli.service.LoginFailureException;
import com.geulkkoli.service.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class LoginTest {

    @Autowired
    LoginService loginService;
    @Autowired
    UsersRepository usersRepository;

    @PostConstruct
    void init(){
        usersRepository.save(Users.builder()
                .userId("kkk")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build());
    }

    @Test
    void 로그인테스트(){
        //given

        //when
        Users loginUser = loginService.login("kkk", "1234");

        //then
        assertThat(loginUser.getUserId()).isEqualTo("kkk");
    }

    @Test
    void 로그인실패시_실패예외를_던진다(){
        //given

        //when

        //then
        assertThatThrownBy(() ->loginService.login("kkk1", "1234")).isInstanceOf(LoginFailureException.class);
    }

}
