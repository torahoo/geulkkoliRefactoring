package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.myPage.dto.edit.UserInfoEditFormDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    User saveUser;


    @BeforeAll
    void init() {
        saveUser = userRepository.save(User.builder() // userId = 3L
                .email("tako1@naver.com").userName("김1").nickName("바나나1").password("123qwe!@#").phoneNo("01012345671").gender("male").build());
    }

    @Test
    @DisplayName("이메일 중복")
    void isEmailDuplicate() {
        assertThat(userService.isEmailDuplicate("tako1@naver.com")).isTrue();
    }

    @Test
    @DisplayName("별명 중복")
    void isNickNameDuplicate() {

        assertThat(userService.isNickNameDuplicate("바나나1")).isTrue();
    }

    @Test
    @DisplayName("전화번호 중복확인")
    void isPhoneNoDuplicate() {

        assertThat(userService.isPhoneNoDuplicate("01012345671")).isTrue();
    }

    @Test
    @DisplayName("회원정보 수정 성공")
    void updateTest() {
        //given
        UserInfoEditFormDto preupdateUser = UserInfoEditFormDto.from("김2","바나나155","01055554646","female");

        //when
        userService.edit(saveUser.getUserId(), preupdateUser);
        Optional<User> one = userRepository.findById(saveUser.getUserId());

        // then
        assertThat(one).get().extracting("userName", "nickName", "phoneNo", "gender").containsExactly("김2", "바나나155", "01055554646", "female");
    }

}
