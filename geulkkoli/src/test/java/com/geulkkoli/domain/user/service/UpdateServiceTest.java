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
    EditService editService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginService loginService;

    @BeforeAll
    void init() {
        User user = userRepository.save(User.builder()
                .email("tako1@naver.com")
                .userName("김1")
                .nickName("바나나1")
                .password("123qwe!@#")
                .phoneNo("01012345671")
                .gender("male")
                .build());

        log.info("user의 userId 조회 = {}", user.getUserId());
    }


    @Test
    @DisplayName("업데이트 테스트")
    void updateTest() {
        //given
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        //when
        EditForm preupdateUser = EditForm.builder()
                .userName("김2")
                .nickName("바나나155")
                .phoneNo("01055554646")
                .gender("female")
                .build();

        editService.update(3L, preupdateUser, mockHttpServletRequest);

        Optional<User> one = userRepository.findById(3L);

        log.info("@Test ::2 one={}", one);

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
        boolean passwordVerification = editService.isPasswordVerification(user.get(), editPasswordForm);
        log.info("비밀번호 일치하는지 확인 = {}", passwordVerification);

        editService.updatePassword(user.get().getUserId(), editPasswordForm);

        // user에는 getPassword가 없으므로 로그인으로 확인
        Optional<User> login = loginService.login(user.get().getEmail(), editPasswordForm.getNewPassword());
        log.info("로그인되는지 확인 = {}", login.toString());

        //then
        Assertions.assertThat(user).isEqualTo(login);
    }

}
