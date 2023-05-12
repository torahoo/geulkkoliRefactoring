package com.geulkkoli.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.geulkkoli.application.security.UserSecurityService;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.post.dto.AddDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.Charset;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostService postService;
    @Autowired
    TestUserDetailService testUserDetailService;

    User user;
    Post post01;

    @BeforeEach
    void init() {
        User save = User.builder()
                .email("email")
                .userName("김")
                .nickName("바나나")
                .phoneNo("0101223671")
                .password("123qwe!@#")
                .gender("Male").build();

        user = userRepository.save(save);

        AddDTO addDTO01 = AddDTO.builder()
                .title("testTitle01")
                .postBody("test postbody 01")
                .nickName(testUserDetailService.loadUserByUsername("바나나").getUsername())
                .build();
        post01 = postService.savePost(addDTO01, user);
    }

    private class Comment {
        public String commentBody;

        public Comment(String commentBody) {
            this.commentBody = commentBody;
        }
    }

    @WithUserDetails(value = "test", userDetailsServiceBeanName = "testUserDetailService")
    @Test
    void writeComment() throws Exception {

        //ginven
        ObjectMapper om = new ObjectMapper();
        om.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        Comment comment = new Comment("test..ing");
        String objJson = om.writeValueAsString(comment);
        MediaType mediaType = new MediaType(
                MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
        Long detailPage = post01.getPostId();


        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/writePostComment/" + detailPage)
                        .contentType(mediaType).content(objJson))

        //than
                .andExpect(MockMvcResultMatchers.status().is(201));
//                .andExpect(MockMvcResultMatchers.);

    }
}
