package com.geulkkoli.domain.admin.service;

import com.geulkkoli.application.security.AccountStatus;
import com.geulkkoli.application.security.Permission;
import com.geulkkoli.application.security.PermissionRepository;
import com.geulkkoli.application.security.UserSecurityService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AdminServiceImplTest {


    @Autowired
    AdminServiceImpl adminServiceImpl;

    @Autowired
    UserSecurityService userSecurityService;


    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionRepository permissionRepository;

    /**
     * TODO: 2023-04-24
     * 유저를 잠근다
     * 전체 테스트를 돌리면 permission이 null이 되어서 테스트가 실패한다.
     * mock을 사용해서 테스트를 진행하게 리팩토링 중이다.
     */
    @Test
    @DisplayName("유저의 계정을 잠근다.")
    @Transactional
    void rockUser() {
        User user = createUser();
        Permission permission = permissionRepository.save(createPermission());
        user.addPermission(permission);
        userRepository.save(user);

        adminServiceImpl.rockUser(user.getUserId());

        User user1 = adminServiceImpl.findUser(1L);
        System.out.println(user1.getPermission());

    }

    private Permission createPermission() {
        return Permission.of(AccountStatus.ACTIVE);
    }

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