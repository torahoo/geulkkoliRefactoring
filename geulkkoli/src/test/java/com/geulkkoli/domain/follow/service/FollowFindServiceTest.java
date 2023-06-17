package com.geulkkoli.domain.follow.service;

import com.geulkkoli.application.follow.FollowInfos;
import com.geulkkoli.application.security.Role;
import com.geulkkoli.application.security.RoleEntity;
import com.geulkkoli.application.security.RoleRepository;
import com.geulkkoli.domain.follow.Follow;
import com.geulkkoli.domain.follow.FollowRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class FollowFindServiceTest {

    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FollowFindService followFindService;


//    @BeforeEach
//    void setUp() {
//        followRepository = Mockito.mock(FollowRepository.class);
//        followFindService = new FollowFindService(followRepository);
//    }
//
//    @Test
//    void findAllFollowerByFolloweeId() {
//
//        User user = User.builder()
//                .email("test@gmail.com")
//                .userName("test")
//                .gender("Male")
//                .password("1234")
//                .nickName("nickName")
//                .phoneNo("010-1234-1234")
//                .build();
//        User user2 = User.builder()
//                .email("test2@gmail.com")
//                .userName("test2").nickName("nickName2")
//                .phoneNo("010-1111-1111")
//                .gender("None")
//                .password("1234")
//                .build();
//
//        User user3 = User.builder()
//                .email("test3@gmail.com")
//                .userName("test3").nickName("nickName3")
//                .phoneNo("010-1112-1111")
//                .gender("None")
//                .password("1234")
//                .build();
//
//        ReflectionTestUtils.setField(user, "userId", 1L);
//        ReflectionTestUtils.setField(user2, "userId", 2L);
//        ReflectionTestUtils.setField(user3, "userId", 3L);
//
//        Follow followEntity = Follow.of(user, user2);
//        Follow followEntity2 = Follow.of(user, user3);
//        List<FollowInfo> followInfos = new ArrayList<>();
//        FollowInfo followInfo = new FollowInfo(followEntity.getId(), followEntity.getFollowee().getUserId(), followEntity.getFollower().getNickName(), followEntity.getCreatedAt());
//        FollowInfo followInfo2 = new FollowInfo(followEntity2.getId(), followEntity2.getFollowee().getUserId(), followEntity2.getFollower().getNickName(), followEntity2.getCreatedAt());
//        followInfos.add(followInfo);
//        followInfos.add(followInfo2);
//
//        given(followRepository.findFollowEntitiesByFolloweeUserId(1L)).willReturn(List.of(followEntity, followEntity2));
//
//        when(followFindService.findSomeFollowerByFolloweeId(1L,null,Pageable.ofSize(10))).thenReturn(followInfos);
//
//
//        List<FollowInfo> allFollower = followFindService.findSomeFollowerByFolloweeId(1L, null,Pageable.ofSize(10));
//
//        then(followRepository).should().findFollowEntitiesByFolloweeUserId(1L);
//
//        assertThat(allFollower).hasSize(2);
//
//    }
//
//    @Test
//    void findAllFolloweeByFollowerId() {
//
//        User user = User.builder()
//                .email("test@gmail.com")
//                .userName("test")
//                .gender("Male")
//                .password("1234")
//                .nickName("nickName")
//                .phoneNo("010-1234-1234")
//                .build();
//        User user2 = User.builder()
//                .email("test2@gmail.com")
//                .userName("test2").nickName("nickName2")
//                .phoneNo("010-1111-1111")
//                .gender("None")
//                .password("1234")
//                .build();
//
//        User user3 = User.builder()
//                .email("test3@gmail.com")
//                .userName("test3").nickName("nickName3")
//                .phoneNo("010-1112-1111")
//                .gender("None")
//                .password("1234")
//                .build();
//
//        ReflectionTestUtils.setField(user, "userId", 1L);
//        ReflectionTestUtils.setField(user2, "userId", 2L);
//        ReflectionTestUtils.setField(user3, "userId", 3L);
//
//        Follow followEntity = Follow.of(user, user2);
//        Follow followEntity2 = Follow.of(user3, user2);
//        List<Follow> followEntities = new ArrayList<>();
//        followEntities.add(followEntity);
//        followEntities.add(followEntity2);
//
//        List<FollowInfo> followInfos = new ArrayList<>();
//        FollowInfo followInfo = new FollowInfo(followEntity.getId(), followEntity.getFollowee().getUserId(), followEntity.getFollower().getNickName(), followEntity.getCreatedAt());
//        FollowInfo followInfo2 = new FollowInfo(followEntity2.getId(), followEntity2.getFollowee().getUserId(), followEntity2.getFollower().getNickName(), followEntity2.getCreatedAt());
//        followInfos.add(followInfo);
//        followInfos.add(followInfo2);
//
//        given(followRepository.findFollowersByFolloweeUserId(user2.getUserId(),1L,10)).willReturn(followInfos);
//
//        when(followFindService.findSomeFollowerByFolloweeId(user2.getUserId(),1L,Pageable.ofSize(10))).thenReturn(followInfos);
//
//        List<FollowInfo> allFollowerByFolloweeId = followFindService.findSomeFollowerByFolloweeId(user2.getUserId(), 1L, Pageable.ofSize(10));
//
//        then(followRepository).should().findFollowersByFolloweeUserId(user2.getUserId(),1L,10);
//
//        assertAll(() -> {
//            assertThat(allFollowerByFolloweeId).hasSize(2);
//            assertThat(allFollowerByFolloweeId.get(0).getUserId()).isEqualTo(1L);
//            assertThat(allFollowerByFolloweeId.get(1).getUserId()).isEqualTo(3L);
//        });
//    }

    @Test
    void findSomeFolloweeByFollowerId() {
        User user = User.builder()
                .email("test@gmail.com")
                .userName("test")
                .gender("Male")
                .password("1234")
                .nickName("nickName")
                .phoneNo("010-1234-1234")
                .build();
        User user2 = User.builder()
                .email("test2@gmail.com")
                .userName("test2").nickName("nickName2")
                .phoneNo("010-1111-1111")
                .gender("None")
                .password("1234")
                .build();

        User user3 = User.builder()
                .email("test3@gmail.com")
                .userName("test3").nickName("nickName3")
                .phoneNo("010-1112-1111")
                .gender("None")
                .password("1234")
                .build();

        User user4 = User.builder()
                .email("test4@gmail.com")
                .userName("test4").nickName("nickName4")
                .phoneNo("010-1113-1111")
                .gender("None")
                .password("1234")
                .build();

        User user5 = User.builder()
                .email("test5@gmail.com")
                .userName("test5").nickName("nickName5")
                .phoneNo("010-1114-1111")
                .gender("None")
                .password("1234")
                .build();

        User user6 = User.builder()
                .email("test6@gmail.com")
                .userName("test6").nickName("nickName6")
                .phoneNo("010-1115-1111")
                .gender("None")
                .password("1234")
                .build();

        RoleEntity roleEntity = user.addRole(Role.USER);
        RoleEntity roleEntity2 = user2.addRole(Role.USER);
        RoleEntity roleEntity3 = user3.addRole(Role.USER);
        RoleEntity roleEntity4 = user4.addRole(Role.USER);
        RoleEntity roleEntity5 = user5.addRole(Role.USER);
        RoleEntity roleEntity6 = user6.addRole(Role.USER);

        roleRepository.save(roleEntity);
        roleRepository.save(roleEntity2);
        roleRepository.save(roleEntity3);
        roleRepository.save(roleEntity4);
        roleRepository.save(roleEntity5);
        roleRepository.save(roleEntity6);

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);

        Follow followEntity = Follow.of(user2, user);
        Follow followEntity2 = Follow.of(user3, user);
        Follow followEntity3 = Follow.of(user4, user);
        Follow followEntity4 = Follow.of(user5, user);

        followRepository.save(followEntity);
        followRepository.save(followEntity2);
        followRepository.save(followEntity3);
        followRepository.save(followEntity4);

        FollowInfos someFolloweeByFollowerId = followFindService.findSomeFolloweeByFollowerId(user.getUserId(), followEntity2.getId(), Pageable.ofSize(3));

        assertThat(someFolloweeByFollowerId.getFollowInfos()).hasSize(1);
    }


    @Test
    void findSomeFollowerByFolloweeId() {
        User user = User.builder()
                .email("test@gmail.com")
                .userName("test")
                .gender("Male")
                .password("1234")
                .nickName("nickName")
                .phoneNo("010-1234-1234")
                .build();
        User user2 = User.builder()
                .email("test2@gmail.com")
                .userName("test2").nickName("nickName2")
                .phoneNo("010-1111-1111")
                .gender("None")
                .password("1234")
                .build();

        User user3 = User.builder()
                .email("test3@gmail.com")
                .userName("test3").nickName("nickName3")
                .phoneNo("010-1112-1111")
                .gender("None")
                .password("1234")
                .build();

        User user4 = User.builder()
                .email("test4@gmail.com")
                .userName("test4").nickName("nickName4")
                .phoneNo("010-1113-1111")
                .gender("None")
                .password("1234")
                .build();

        User user5 = User.builder()
                .email("test5@gmail.com")
                .userName("test5").nickName("nickName5")
                .phoneNo("010-1114-1111")
                .gender("None")
                .password("1234")
                .build();

        User user6 = User.builder()
                .email("test6@gmail.com")
                .userName("test6").nickName("nickName6")
                .phoneNo("010-1115-1111")
                .gender("None")
                .password("1234")
                .build();

        RoleEntity roleEntity = user.addRole(Role.USER);
        RoleEntity roleEntity2 = user2.addRole(Role.USER);
        RoleEntity roleEntity3 = user3.addRole(Role.USER);
        RoleEntity roleEntity4 = user4.addRole(Role.USER);
        RoleEntity roleEntity5 = user5.addRole(Role.USER);
        RoleEntity roleEntity6 = user6.addRole(Role.USER);

        roleRepository.save(roleEntity);
        roleRepository.save(roleEntity2);
        roleRepository.save(roleEntity3);
        roleRepository.save(roleEntity4);
        roleRepository.save(roleEntity5);
        roleRepository.save(roleEntity6);

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);

        Follow followEntity = Follow.of(user, user2);
        Follow followEntity2 = Follow.of(user, user3);
        Follow followEntity3 = Follow.of(user, user4);
        Follow followEntity4 = Follow.of(user, user5);

        followRepository.save(followEntity);
        followRepository.save(followEntity2);
        followRepository.save(followEntity3);
        followRepository.save(followEntity4);

        FollowInfos someFollowerByFolloweeId = followFindService.findSomeFollowerByFolloweeId(user.getUserId(), followEntity2.getId(), Pageable.ofSize(3));

        assertThat(someFollowerByFolloweeId.getFollowInfos()).hasSize(1);


    }
}