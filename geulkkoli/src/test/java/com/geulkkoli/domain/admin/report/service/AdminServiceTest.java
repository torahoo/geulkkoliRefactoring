package com.geulkkoli.domain.admin.report.service;

import com.geulkkoli.application.security.UserSecurityService;
import com.geulkkoli.application.user.*;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.JoinFormDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @InjectMocks
    AdminService adminService;
    @InjectMocks
    UserSecurityService userSecurityService;

    @Mock
    UserRepository userRepository;
    @Mock
    PermissionRepository permissionRepository;

//    @BeforeEach
//    void setUp() {
//        JoinFormDto joinForm = new JoinFormDto();
//        joinForm.setEmail("tako111@naver.com");
//        joinForm.setUserName("김");
//        joinForm.setNickName("바나나1322");
//        joinForm.setPhoneNo("0101223671");
//        joinForm.setGender("male");
//        joinForm.setPassword("123qwe!@#");
//
//        User user = userSecurityService.join(joinForm);
//    }

    /**
     * TODO: 2023-04-24
     * 유저를 잠근다
     * 전체 테스트를 돌리면 permission이 null이 되어서 테스트가 실패한다.
     * mock을 사용해서 테스트를 진행하게 리팩토링 중이다.
     */
    @Test
    @DisplayName("유저의 계정을 잠근다.")
    void rockUser() {
        //given
        User user = createUser();
        Long fakeId = 1L;
        ReflectionTestUtils.setField(user, "userId", fakeId);
        Permission permission = createPermission(user);
        ReflectionTestUtils.setField(permission, "id", fakeId);
        ReflectionTestUtils.setField(user, "permission", permission);

        //mocking
        given(userRepository.save(any())).willReturn(user);
        given(userRepository.findById(fakeId)).willReturn(Optional.of(user));

        adminService.rockUser(1L);
        Optional<User> findByUser = userRepository.findById(1L);

        System.out.println("findByUser = " + findByUser.get().getPermission().getAccountActivityElement().isAccountNonLocked());
    }

    private Permission createPermission(User user) {
        return Permission.of(AccountActivityElement.builder().isEnabled(true).isAccountNonLocked(false)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true).build(), AccountStatus.ACTIVE);
    }

    private  User createUser() {
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