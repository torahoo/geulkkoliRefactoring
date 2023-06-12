package com.geulkkoli.domain.follow.service;

import com.geulkkoli.application.follow.FollowInfo;
import com.geulkkoli.domain.follow.Follow;
import com.geulkkoli.domain.follow.FollowRepository;
import com.geulkkoli.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

class FollowFindServiceTest {
    private FollowRepository followRepository;

    private FollowFindService followFindService;


    @BeforeEach
    void setUp() {
        followRepository = Mockito.mock(FollowRepository.class);
        followFindService = new FollowFindService(followRepository);
    }

    @Test
    void findAllFollowerByFolloweeId() {

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

        ReflectionTestUtils.setField(user, "userId", 1L);
        ReflectionTestUtils.setField(user2, "userId", 2L);
        ReflectionTestUtils.setField(user3, "userId", 3L);

        Follow followEntity = Follow.of(user, user2);
        Follow followEntity2 = Follow.of(user, user3);
        List<FollowInfo> followInfos = new ArrayList<>();
        FollowInfo followInfo = new FollowInfo(followEntity.getId(), followEntity.getFollowee().getUserId(), followEntity.getFollower().getNickName(), followEntity.getCreatedAt());
        FollowInfo followInfo2 = new FollowInfo(followEntity2.getId(), followEntity2.getFollowee().getUserId(), followEntity2.getFollower().getNickName(), followEntity2.getCreatedAt());
        followInfos.add(followInfo);
        followInfos.add(followInfo2);

        given(followRepository.findFollowEntitiesByFolloweeUserId(1L)).willReturn(List.of(followEntity, followEntity2));

        when(followFindService.findSomeFollowerByFolloweeId(1L,null,Pageable.ofSize(10))).thenReturn(followInfos);


        List<FollowInfo> allFollower = followFindService.findSomeFollowerByFolloweeId(1L, null,Pageable.ofSize(10));

        then(followRepository).should().findFollowEntitiesByFolloweeUserId(1L);

        assertThat(allFollower).hasSize(2);

    }

    @Test
    void findAllFolloweeByFollowerId() {

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

        ReflectionTestUtils.setField(user, "userId", 1L);
        ReflectionTestUtils.setField(user2, "userId", 2L);
        ReflectionTestUtils.setField(user3, "userId", 3L);

        Follow followEntity = Follow.of(user, user2);
        Follow followEntity2 = Follow.of(user3, user2);
        List<Follow> followEntities = new ArrayList<>();
        followEntities.add(followEntity);
        followEntities.add(followEntity2);

        List<FollowInfo> followInfos = new ArrayList<>();
        FollowInfo followInfo = new FollowInfo(followEntity.getId(), followEntity.getFollowee().getUserId(), followEntity.getFollower().getNickName(), followEntity.getCreatedAt());
        FollowInfo followInfo2 = new FollowInfo(followEntity2.getId(), followEntity2.getFollowee().getUserId(), followEntity2.getFollower().getNickName(), followEntity2.getCreatedAt());
        followInfos.add(followInfo);
        followInfos.add(followInfo2);
        given(followRepository.findFolloweesByFollowerUserId(user2.getUserId(),null,10)).willReturn(followInfos);

        when(followFindService.findSomeFollowerByFolloweeId(user2.getUserId(),null,Pageable.ofSize(10))).thenReturn(followInfos);

        List<FollowInfo> allFollowerByFolloweeId = followFindService.findSomeFollowerByFolloweeId(user2.getUserId(), null, Pageable.ofSize(10));

        then(followRepository).should().findFolloweesByFollowerUserId(user2.getUserId(),null,10);

        assertAll(() -> {
            assertThat(allFollowerByFolloweeId).hasSize(2);
            assertThat(allFollowerByFolloweeId.get(0).getUserId()).isEqualTo(1L);
            assertThat(allFollowerByFolloweeId.get(1).getUserId()).isEqualTo(3L);
        });
    }
}