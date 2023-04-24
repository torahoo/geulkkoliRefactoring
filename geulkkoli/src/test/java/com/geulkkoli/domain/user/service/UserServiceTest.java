package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.JoinFormDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void isEmailDuplicate() {
        User user = User.builder()
                .email("tako11@namver.com")
                .userName("김11")
                .nickName("바나나111")
                .password("123412")
                .gender("male")
                .build();

        given(userRepository.save(user)).willReturn(user);


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
