package com.geulkkoli.domain.admin.service;

import com.geulkkoli.application.user.UserSecurityService;
import com.geulkkoli.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminServiceTest {


    @Autowired
    AdminService adminServiceImpl;

    @Autowired
    UserSecurityService userSecurityService;




    private User createUser() {
        return User.builder()
                .email("tako111@naver.com")
                .userName("김")
                .nickName("바나나1322")
                .phoneNo("0101223671")
                .gender("male")
                .password("123qwe!@#")
                .build();
    }
}