package com.geulkkoli.domain.follow;

import com.geulkkoli.application.follow.FollowInfo;
import com.geulkkoli.application.security.Role;
import com.geulkkoli.application.security.RoleRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@DataJpaTest
class FollowRepositoryImplTest {

    @Autowired
    private FollowRepositoryImpl followRepositoryDsl;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findFollowEntitiesByFolloweeUserId() {
        User user = User.builder()
                .email("test@gmail.com")
                .nickName("test")
                .password("1234")
                .phoneNo("010-1111-2222")
                .gender("Male")
                .userName("tester1")
                .build();


        User user2 = User.builder()
                .email("test1@gmail.com")
                .nickName("test1")
                .password("1234")
                .phoneNo("010-2222-3333")
                .nickName("test1")
                .userName("tester2")
                .gender("female")
                .build();

        User user3 = User.builder()
                .email("test3@gmail.com")
                .nickName("test3")
                .password("1234")
                .phoneNo("010-3333-4444")
                .userName("tester3")
                .gender("None")
                .build();

        User user4 = User.builder()
                .email("test4@gmail.com")
                .nickName("test4")
                .password("1234")
                .phoneNo("010-4444-5555")
                .userName("tester")
                .gender("noe")
                .build();

        roleRepository.save(user.addRole(Role.USER));
        roleRepository.save(user2.addRole(Role.USER));
        roleRepository.save(user3.addRole(Role.USER));
        roleRepository.save(user4.addRole(Role.USER));
        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        Follow follow = Follow.of(user, user2);
        Follow follow2 = Follow.of(user, user3);
        Follow follow3 = Follow.of(user, user4);

        followRepository.save(follow);
        followRepository.save(follow2);
        followRepository.save(follow3);

        List<FollowInfo> followEntitiesByFolloweeUserId = followRepositoryDsl.findFollowEntitiesByFolloweeUserId(user.getUserId(), null, 3);

        assertAll(() -> {
            assertThat(followEntitiesByFolloweeUserId).size().isEqualTo(3);
            assertThat(followEntitiesByFolloweeUserId.stream().findFirst()).get().hasFieldOrPropertyWithValue("nickName", user4.getNickName());
            assertThat(followEntitiesByFolloweeUserId.get(1).getNickName()).isEqualTo(user3.getNickName());
        });
    }


    @Test
    void findFollowEntitiesByFollowerUserId() {
    }
}