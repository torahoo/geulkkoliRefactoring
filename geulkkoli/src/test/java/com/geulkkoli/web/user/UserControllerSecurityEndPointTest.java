package com.geulkkoli.web.user;

import com.geulkkoli.application.security.UserSecurityService;
import com.geulkkoli.web.user.dto.JoinFormDto;
import com.geulkkoli.web.user.dto.UserInfoEditDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerSecurityEndPointTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    UserSecurityService userSecurityService;

    @Autowired
    WebApplicationContext context;


    @Test
    @DisplayName("인증되지 않은 사용자가 /user/edit에 접근하면 로그인 페이지로 리다이렉트")
    void not_Authenticated_user_access_userinfo_edit_redirect_loginPage() throws Exception {

        mvc.perform(get("/user/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/loginPage"));
    }

    @Test
    @DisplayName("인증되지 않은 사용자가 /user/edit에 post 방식으로 보내면 로그인 페이지로 리다이렉트")
    void not_Authenticated_user_access_userinfo_edit_post_redirect_loginPage() throws Exception {

        mvc.perform(post("/user/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/loginPage"));
    }

    @Test
    @DisplayName("인증된 사용자가 /user/edit get에 접근하면 성공")
    void userEditAuthenticated() throws Exception {


        JoinFormDto joinForm = new JoinFormDto();
        joinForm.setEmail("tako99@naver.com");
        joinForm.setUserName("김");
        joinForm.setNickName("바나나11");
        joinForm.setPhoneNo("9190232333");
        joinForm.setGender("male");
        joinForm.setPassword("qwe123!!!");
        userSecurityService.join(joinForm);

        UserDetails user = userSecurityService.loadUserByUsername("tako99@naver.com");

        ResultActions result = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build().perform(
                        get("/user/edit")
                                .with(user(user)));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("인증된 사용자가 /user/edit post로 정보를 올바르게 수정하면 user/edit으로 리다렉트한다.")
    void userinfoModifySuccess() throws Exception {
        UserInfoEditDto userInfoEditDto =
                UserInfoEditDto.builder().userName("tako99@naver.com")
                        .nickName("바나나11")
                        .phoneNo("9190232333")
                        .gender("male")
                        .build();

        JoinFormDto joinForm = new JoinFormDto();
        joinForm.setEmail("tako99@naver.com");
        joinForm.setUserName("김");
        joinForm.setNickName("바나나11");
        joinForm.setPhoneNo("9190232333");
        joinForm.setGender("male");
        joinForm.setPassword("qwe123!!!");
        userSecurityService.join(joinForm);

        UserDetails user = userSecurityService.loadUserByUsername("tako99@naver.com");

        ResultActions resultActions = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build().perform(
                        post("/user/edit")
                                .with(user(user))
                                .param("userName", userInfoEditDto.getUserName())
                                .param("nickName", userInfoEditDto.getNickName())
                                .param("phoneNo", userInfoEditDto.getPhoneNo())
                                .param("gender", userInfoEditDto.getGender())
                                .content(userInfoEditDto.toString()));


        resultActions.andExpect(status().is(302))
                .andExpect(redirectedUrl("/user/edit"));
    }

}
