package com.geulkkoli.web.user;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(UserController.class)
class UserControllerTest {
    public static final String TESTER_MAIL = "tester@naver.com";
    public static final String TESTER_PASSWORD = "qwe123!@";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    User user;
    @BeforeEach
    public void init() {
        user = User.builder()
                .email(TESTER_MAIL)
                .userName("유저이름1")
                .nickName("유저닉1")
                .password(TESTER_PASSWORD)
                .phoneNo("01012345678")
                .gender("male")
                .build();
    }

    @Test
    void userLogin() throws Exception {
        //given
        given(userService.login(any(), any())).willReturn(Optional.of(user));

        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("email", TESTER_MAIL);
        query_param.add("password", TESTER_PASSWORD);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .params(query_param))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("세션이 상태유지를 하는 지 테스트 한다.")
    void isSessionStateful() throws Exception {
        given(userService.login(any(), any())).willReturn(Optional.of(user));

        MockHttpSession session = new MockHttpSession();
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("email", TESTER_MAIL)
                        .param("password", TESTER_PASSWORD)
                        .session(session))

                //then
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        assertThat(session.getAttribute(SessionConst.LOGIN_USER)).isNotNull();
    }

    @Test
    @DisplayName("회원가입 잘 저장 되는지 테스트")
    void joinUserTest() throws Exception {
        //given
        User joinUser=User.builder()
                .userName("fishs")
                .password("qwe123!@")
                .nickName("takos")
                .email("takodachi@naver.com")
                .phoneNo("01071397733")
                .gender("male")
                .build();

        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(joinUser));

        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("userName", "fishs");
        query_param.add("password", "qwe123!@");
        query_param.add("verifyPassword", "1234WnRnal@");
        query_param.add("nickName", "takos");
        query_param.add("email", "takodachi@naver.com");
        query_param.add("phoneNo", "01071397733");
        query_param.add("gender", "male");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                .params(query_param));

        User findByLoginIdUser = userRepository.findByEmail("takodachi@naver.com").
                orElse(null);

        //then
        assertThat(findByLoginIdUser).isNotNull();
        assertThat(findByLoginIdUser.getGender()).isEqualTo("male");
    }

    @Test
    @DisplayName("회원가입 이메일 중복체크 되는지 테스트")
    void joinEmailDupleTest() throws Exception {
        //given
        given(userRepository.findByEmail("tako@naver.com")).willReturn(Optional.ofNullable(user));
        given(userService.isEmailDuplicate(TESTER_MAIL)).willReturn(true);
        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("userName", "fish");
        query_param.add("password", "1234WnRnal@");
        query_param.add("verifyPassword", "1234WnRnal@");
        query_param.add("nickName", "타코메일");
        query_param.add("email", TESTER_MAIL);
        query_param.add("phoneNo", "01071397733");
        query_param.add("gender", "male");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .params(query_param))
                .andExpect(MockMvcResultMatchers.status().isOk());
        User findByEmailIdUser = userRepository.findByEmail("tako@naver.com").
                orElse(null);

        //then
        assertThat(findByEmailIdUser).isNotNull();
        assertThat(findByEmailIdUser.getUserName()).isEqualTo("유저이름1");
        assertThat(findByEmailIdUser.getUserName()).doesNotContain("fish");
    }

    @Test
    @DisplayName("회원가입 닉네임 중복 체크 되는지 테스트")
    void joinNickNameDupleTest() throws Exception {
        given(userService.isNickNameDuplicate("유저닉1")).willReturn(true);

        //given
        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("userName", "fish");
        query_param.add("password", "WnRnal");
        query_param.add("verifyPassword", "1234WnRnal@");
        query_param.add("nickName", "유저닉1");
        query_param.add("email", "takoNickTest@naver.com");
        query_param.add("phoneNo", "01071397733");
        query_param.add("gender", "male");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .params(query_param))
                .andExpect(MockMvcResultMatchers.status().isOk());
        User findByEmailIdUser = userRepository.findByEmail("takoNickTest@naver.com").
                orElse(null);

        //then
        assertThat(findByEmailIdUser).isNull();
    }

    @Test
    @DisplayName("전화번호 중복 체크 되는지 테스트")
    void joinPhoneNoDupleTest() throws Exception {
        given(userService.isPhoneNoDuplicate("01012345678")).willReturn(true);
        //given
        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("userName", "fish");
        query_param.add("password", "1234WnRnal@");
        query_param.add("verifyPassword", "1234WnRnal@");
        query_param.add("nickName", "takodachi");
        query_param.add("email", "takos@naver.com");
        query_param.add("phoneNo", "01012345678");
        query_param.add("gender", "male");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .params(query_param))
                .andExpect(MockMvcResultMatchers.status().isOk());
        User rejectUser = userRepository.findByNickName("takodachi").
                orElse(null);

        //then
        assertThat(rejectUser).isNull();
    }
}