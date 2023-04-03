package com.geulkkoli.web.user;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
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
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("세션이 상태유지를 하는 지 테스트 한다.")
    void isSessionStateful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("email", "tako@naver.com")
                        .param("password", "1234"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원가입 잘 저장 되는지 테스트")
    void joinUserTest() throws Exception {
        //given
        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("userName", "fish");
        query_param.add("password", "1234");
        query_param.add("nickName", "takos");
        query_param.add("email", "tako@naver.com");
        query_param.add("phoneNo", "01071397733");
        query_param.add("sex", "male");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .params(query_param))
                .andExpect(MockMvcResultMatchers.status().isOk());
        User findByLoginIdUser = userRepository.findByEmail("clever").
                orElse(null);

        //then
        if (findByLoginIdUser != null) {
            assertThat(findByLoginIdUser.getSex()).isEqualTo("male");
        }
    }

    @Test
    @DisplayName("회원가입 중복 체크 되는지 테스트")
    void joinUserDupleTest() throws Exception {
        //given
        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("userName", "fish");
        query_param.add("password", "1234");
        query_param.add("nickName", "바나나");
        query_param.add("email", "tako@naver.com");
        query_param.add("phoneNo", "01071397733");
        query_param.add("sex", "male");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .params(query_param))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("idDuple"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("emailDuple"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("nickNameDuple"));
        User findByEmailIdUser = userRepository.findByEmail("tako@naver.com").
                orElse(null);

        //then
        if (findByEmailIdUser != null) {
            assertThat(findByEmailIdUser.getUserName()).isEqualTo("김");
        }
    }

    @Test
    @DisplayName("회원가입 중복 저장 잘 막는지 테스트")
    void joinUserDupleRejectTest() throws Exception {
        //given
        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("userName", "fish");
        query_param.add("password", "1234");
        query_param.add("nickName", "takodachi");
        query_param.add("email", "tako@naver.com");
        query_param.add("phoneNo", "01071397733");
        query_param.add("sex", "male");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .params(query_param))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("idDuple"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("emailDuple"));
        User rejectUser = userRepository.findByNickName("takodachi").
                orElse(null);

        //then
        assertThat(rejectUser).isEqualTo(null);

    }
}