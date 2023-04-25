package com.geulkkoli.domain.admin.service;

import com.geulkkoli.application.security.Permission;
import com.geulkkoli.application.security.UserSecurityService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AdminServiceImplTest {


    @Autowired
    AdminServiceImpl adminServiceImpl;

    @Autowired
    UserSecurityService userSecurityService;


    @Autowired
    UserRepository userRepository;



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