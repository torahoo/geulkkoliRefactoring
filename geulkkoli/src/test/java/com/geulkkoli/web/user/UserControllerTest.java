package com.geulkkoli.web.user;

import com.geulkkoli.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    MockHttpSession mockHttpSession;

    @PostConstruct // 1
    public void setUp() throws Exception {
        mockHttpSession = new MockHttpSession();

        mockHttpSession.setAttribute("loginUser", "테스트");
    }


    @Test
    void userLogin() throws Exception{
        //given
        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("userId", "kkk");
        query_param.add("password", "1234");

        LoginForm loginForm = new LoginForm();
        loginForm.setUserId("kkk");
        loginForm.setPassword("1234");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .params(query_param))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("세션이 상태유지를 하는 지 테스트 한다.")
    void isSessionStateful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.log());
    }
}