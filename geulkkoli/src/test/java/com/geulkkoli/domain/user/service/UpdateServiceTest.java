package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.edit.EditForm;
import com.geulkkoli.web.user.edit.EditPasswordForm;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@SpringBootTest
@Transactional
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdateServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeAll
    void init() {
        userRepository.save(User.builder() // userId = 3L
                .email("tako1@naver.com")
                .userName("김1")
                .nickName("바나나1")
                .password("123qwe!@#")
                .phoneNo("01012345671")
                .gender("male")
                .build());
    }


    @Test
    @DisplayName("업데이트 테스트")
    void updateTest() {
        //given
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        EditForm preupdateUser = EditForm.builder()
                .userName("김2")
                .nickName("바나나155")
                .phoneNo("01055554646")
                .gender("female")
                .build();

        //when
        userService.update(3L, preupdateUser);
        Optional<User> one = userRepository.findById(3L);

        // then
        Assertions.assertThat("바나나155").isEqualTo(one.get().getNickName());
    }

    @Test
    @DisplayName("비밀번호 업데이트 테스트")
    void passwordTest() {

        //given
        Optional<User> user = userRepository.findById(3L);

        EditPasswordForm editPasswordForm = new EditPasswordForm();
        editPasswordForm.setPassword("123qwe!@#");
        editPasswordForm.setNewPassword("abc123!@#");
        editPasswordForm.setVerifyPassword("abc123!@#");

        //when
        boolean passwordVerification = userService.isPasswordVerification(user.get(), editPasswordForm);

        if (passwordVerification)
            userService.updatePassword(user.get().getUserId(), editPasswordForm);

        //user에는 getPassword가 없으므로 로그인으로 확인
        Optional<User> login = userService.login(user.get().getEmail(), editPasswordForm.getNewPassword());

        //then
        Assertions.assertThat(user).isEqualTo(login);
    }

}
