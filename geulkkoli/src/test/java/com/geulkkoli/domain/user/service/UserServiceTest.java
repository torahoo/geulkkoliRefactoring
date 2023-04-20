package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.JoinFormDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;
    @BeforeEach
    void init() {
        JoinFormDto joinForm = new JoinFormDto();
        joinForm.setGender("male");
        joinForm.setPassword("123412");
        joinForm.setPhoneNo("01012345679");
        joinForm.setEmail("tako11@naver.com");
        joinForm.setNickName("바나나111");
        joinForm.setUserName("김11");

        userService.join(joinForm);
    }

    @Test
    void isEmailDuplicate() {

        assertThat(userService.isEmailDuplicate("tako11@naver.com")).isTrue();
    }

    @Test
    void isNickNameDuplicate() {

        assertThat(userService.isNickNameDuplicate("바나나111")).isTrue();
    }

    @Test
    void isPhoneNoDuplicate() {


        assertThat(userService.isPhoneNoDuplicate("01012345679")).isTrue();
    }


}
