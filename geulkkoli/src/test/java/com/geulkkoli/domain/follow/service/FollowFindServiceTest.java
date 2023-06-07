package com.geulkkoli.domain.follow.service;

import com.geulkkoli.domain.follow.FollowEntity;
import com.geulkkoli.domain.follow.FollowRepository;
import com.geulkkoli.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

        FollowEntity followEntity = FollowEntity.of(user, user2);
        FollowEntity followEntity2 = FollowEntity.of(user, user3);
        List<FollowEntity> followEntities = new ArrayList<>();
        followEntities.add(followEntity);
        followEntities.add(followEntity2);

        given(followRepository.findFollowEntitiesByFollowee_UserId(1L)).willReturn(followEntities);

        when(followFindService.findAllFollowerByFolloweeId(1L)).thenReturn(followEntities);


        List<FollowEntity> allFollower = followFindService.findAllFollowerByFolloweeId(1L);

        then(followRepository).should().findFollowEntitiesByFollowee_UserId(1L);

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

        FollowEntity followEntity = FollowEntity.of(user, user2);
        FollowEntity followEntity2 = FollowEntity.of(user3, user2);

        given(followRepository.findFollowEntitiesByFollower_UserId(user.getUserId()));

        when(followFindService.findAllFolloweeByFollowerId(user.getUserId()));

        followFindService.findAllFollowerByFolloweeId(user.getUserId());

    }
}