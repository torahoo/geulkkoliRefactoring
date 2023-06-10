package com.geulkkoli.domain.follow.service;

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
        List<Follow> followEntities = new ArrayList<>();
        followEntities.add(followEntity);
        followEntities.add(followEntity2);

        given(followRepository.findFollowEntitiesByFolloweeUserId(1L)).willReturn(followEntities);

        when(followFindService.findAllFollowerByFolloweeId(1L,Pageable.ofSize(10))).thenReturn(followEntities);


        List<Follow> allFollower = followFindService.findAllFollowerByFolloweeId(1L, Pageable.ofSize(10));

        then(followRepository).should().findFollowEntitiesByFolloweeUserId(1L);

        assertAll(() -> {
            assertThat(allFollower.get(0).getFollowee().getNickName()).isEqualTo("nickName");
            assertThat(allFollower.get(0).getFollower().getNickName()).isEqualTo("nickName2");
            assertThat(allFollower.get(1).getFollowee().getNickName()).isEqualTo("nickName");
            assertThat(allFollower.get(1).getFollower().getNickName()).isEqualTo("nickName3");
        });

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
        given(followRepository.findFollowEntitiesByFollowerUserId(user2.getUserId())).willReturn(followEntities);

        when(followFindService.findAllFollowerByFolloweeId(user2.getUserId(),Pageable.ofSize(10))).thenReturn(followEntities);

        List<Follow> allFollowerByFolloweeId = followFindService.findAllFollowerByFolloweeId(user2.getUserId(), Pageable.ofSize(10));

        then(followRepository).should().findFollowEntitiesByFollowerUserId(user2.getUserId());

        assertAll(() -> {
            assertThat(allFollowerByFolloweeId.get(0).getFollowee().getNickName()).isEqualTo("nickName");
            assertThat(allFollowerByFolloweeId.get(0).getFollower().getNickName()).isEqualTo("nickName2");
            assertThat(allFollowerByFolloweeId.get(1).getFollowee().getNickName()).isEqualTo("nickName3");
            assertThat(allFollowerByFolloweeId.get(1).getFollower().getNickName()).isEqualTo("nickName2");
        });
    }
}