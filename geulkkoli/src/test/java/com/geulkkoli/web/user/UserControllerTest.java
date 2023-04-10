package com.geulkkoli.web.user;

import com.geulkkoli.application.security.config.SecurityConfig;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Test
    void userLogin() throws Exception {
        //given
        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("email", "tako@naver.com");
        query_param.add("password", "1234");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .params(query_param))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("세션이 상태유지를 하는 지 테스트 한다.")
    void isSessionStateful() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("email", "tako@naver.com")
                        .param("password", "1234"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andReturn();

        assertThat(mvcResult.getResponse().getRedirectedUrl()).endsWith("/");
    }

    @Test
    @DisplayName("회원가입 잘 저장 되는지 테스트")
    void joinUserTest() throws Exception {
        //given
        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("userName", "fishs");
        query_param.add("password", "1234WnRnal@");
        query_param.add("verifyPassword", "1234WnRnal@");
        query_param.add("nickName", "takos");
        query_param.add("email", "takodachi@naver.com");
        query_param.add("phoneNo", "01071397733");
        query_param.add("gender", "male");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .params(query_param))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        User findByLoginIdUser = userRepository.findByEmail("takodachi@naver.com").
                orElse(null);


        assert findByLoginIdUser != null;
        assertThat(findByLoginIdUser.getGender()).isEqualTo("male");
    }

    @Test
    @DisplayName("회원가입 이메일 중복체크 되는지 테스트")
    void joinEmailDupleTest() throws Exception {
        //given
        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("userName", "fish");
        query_param.add("password", "1234WnRnal@");
        query_param.add("verifyPassword", "1234WnRnal@");
        query_param.add("nickName", "타코메일");
        query_param.add("email", "tako@naver.com");
        query_param.add("phoneNo", "01071397733");
        query_param.add("gender", "male");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .params(query_param))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeHasErrors("joinForm"));
        User findByEmailIdUser = userRepository.findByEmail("tako@naver.com").
                orElse(null);

        //then
        assert findByEmailIdUser != null;
        assertThat(findByEmailIdUser.getUserName()).isEqualTo("김");
    }

    @Test
    @DisplayName("회원가입 닉네임 중복 체크 되는지 테스트")
    void joinNickNameDupleTest() throws Exception {
        //given
        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("userName", "fish");
        query_param.add("password", "WnRnal");
        query_param.add("verifyPassword", "1234WnRnal@");
        query_param.add("nickName", "바나나");
        query_param.add("email", "takoNickTest@naver.com");
        query_param.add("phoneNo", "01071397733");
        query_param.add("gender", "male");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .params(query_param))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeHasErrors("joinForm"));
        User findByEmailIdUser = userRepository.findByEmail("takoNickTest@naver.com").
                orElse(null);

        //then
            assertThat(findByEmailIdUser).isNull();
    }

    @Test
    @DisplayName("회원가입 중복 저장 잘 막는지 테스트")
    void joinUserDupleRejectTest() throws Exception {
        //given
        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("userName", "fish");
        query_param.add("password", "1234WnRnal@");
        query_param.add("verifyPassword", "1234WnRnal@");
        query_param.add("nickName", "takodachi");
        query_param.add("email", "takos@naver.com");
        query_param.add("phoneNo", "01071397733");
        query_param.add("gender", "male");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .params(query_param))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeHasErrors("joinForm"));
        User rejectUser = userRepository.findByNickName("takodachi").
                orElse(null);

        //then
        assertThat(rejectUser).isNull();

    }
}