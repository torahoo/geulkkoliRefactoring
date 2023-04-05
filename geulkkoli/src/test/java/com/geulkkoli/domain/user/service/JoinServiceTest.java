package com.geulkkoli.domain.user.service;

import com.geulkkoli.web.user.JoinForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Transactional
class JoinServiceTest {

    @Autowired JoinService joinService;

    @Test
    void isEmailDuplicate() {
        JoinForm joinForm = new JoinForm();
        joinForm.setGender("male");
        joinForm.setPassword("123412");
        joinForm.setPhoneNo("01012345679");
        joinForm.setEmail("tako@naver1.com");
        joinForm.setNickName("바나나1");
        joinForm.setUserName("김1");

        joinService.join(joinForm.toEntity());

        assertThat(joinService.isEmailDuplicate("tako@naver1.com")).isTrue();
    }

    @Test
    void isNickNameDuplicate() {

        JoinForm joinForm = new JoinForm();
        joinForm.setGender("male");
        joinForm.setPassword("123412");
        joinForm.setPhoneNo("01012345679");
        joinForm.setEmail("tako@naver1.com");
        joinForm.setNickName("바나나1");
        joinForm.setUserName("김1");

        joinService.join(joinForm.toEntity());

        assertThat(joinService.isNickNameDuplicate("바나나1")).isTrue();
    }

    @Test
    void isPhoneNoDuplicate() {
        JoinForm joinForm = new JoinForm();
        joinForm.setGender("male");
        joinForm.setPassword("123412");
        joinForm.setPhoneNo("01012345679");
        joinForm.setEmail("tako@naver1.com");
        joinForm.setNickName("바나나1");
        joinForm.setUserName("김1");

        joinService.join(joinForm.toEntity());

        assertThat(joinService.isPhoneNoDuplicate("01012345679")).isTrue();
    }

}