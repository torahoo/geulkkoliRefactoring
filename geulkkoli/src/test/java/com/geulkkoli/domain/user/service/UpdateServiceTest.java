package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.edit.EditFormDto;
import com.geulkkoli.web.user.edit.EditPasswordFormDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    User save;

    @BeforeAll
    void init() {
        save = userRepository.save(User.builder()
                .email("tako1@naver.com")
                .userName("김1")
                .nickName("바나나1")
                .password(passwordEncoder.encode("123qwe!@#"))
                .phoneNo("01012345671")
                .gender("male")
                .build());
    }


    @Test
    @DisplayName("업데이트 테스트")
    void updateTest() {
        //given
        EditFormDto preupdateUser = EditFormDto.builder()
                .userName("김2")
                .nickName("바나나155")
                .phoneNo("01055554646")
                .gender("female")
                .build();

        //when
        userService.update(save.getUserId(), preupdateUser);
        Optional<User> one = userRepository.findById(save.getUserId());

        // then
        Assertions.assertThat("바나나155").isEqualTo(one.get().getNickName());
    }

    @Test
    @DisplayName("비밀번호 업데이트 테스트")
    void passwordTest() {

        //given
        Optional<User> user = userRepository.findById(save.getUserId());

        EditPasswordFormDto editPasswordFormDto = new EditPasswordFormDto();
        editPasswordFormDto.setPassword("123qwe!@#");
        editPasswordFormDto.setNewPassword("abc123!@#");
        editPasswordFormDto.setVerifyPassword("abc123!@#");

        //when
        boolean passwordVerification = userService.isPasswordVerification(user.get().getUserId(), editPasswordFormDto.getPassword());

        if (passwordVerification)
            userService.updatePassword(user.get().getUserId(), editPasswordFormDto.getNewPassword());

        //then
        passwordEncoder.matches(user.get().getPassword(), editPasswordFormDto.getNewPassword());
    }

}
