package com.geulkkoli.application.follow;

import com.geulkkoli.domain.follow.Follow;
import com.geulkkoli.domain.follow.service.FollowFindService;
import com.geulkkoli.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

class FollowMyPageUserInfoServiceTest {
    private FollowMyPageUserInfoService followMyPageUserInfoService;
    private FollowFindService followFindService;


    @BeforeEach
    void setUp() {
        followFindService = Mockito.mock(FollowFindService.class);
        followMyPageUserInfoService = new FollowMyPageUserInfoService(followFindService);
    }

    @Test
    @DisplayName("view 단에 보여줄 팔로워 유저 정보")
    void viewFollowerSimpleUserInfo() {
        User user = User.builder()
                .email("test1@gmail.com")
                .userName("test1")
                .phoneNo("111-1111-1111")
                .nickName("nickName1")
                .password("1234")
                .gender("male")
                .build();

        User user2 = User.builder()
                .email("test2@gmail.com")
                .userName("test2")
                .phoneNo("222-2222-2222")
                .nickName("nickName2")
                .password("234")
                .gender("Male")
                .build();

        User user3 = User.builder()
                .email("test3@gmail.com")
                .userName("test3")
                .phoneNo("111-2222-3333")
                .nickName("nickName3")
                .gender("Female")
                .build();

        ReflectionTestUtils.setField(user, "userId", 1L);
        ReflectionTestUtils.setField(user2, "userId", 2L);
        ReflectionTestUtils.setField(user3, "userId", 3L);

        Follow followEntity = Follow.of(user, user2);
        Follow followEntity2 = Follow.of(user3, user2);

        List<Follow> followEntities = List.of(followEntity, followEntity2);
        given(followFindService.findAllFolloweeByFollowerId(user2.getUserId())).willReturn(followEntities);
        List<MyPageUserInfo> mypageUserInfos = new ArrayList<>();

        MyPageUserInfo myPageUserInfo = MyPageUserInfo.of(user.getNickName(),LocalDateTime.now().toString());
        MyPageUserInfo myPageUserInfo2 = MyPageUserInfo.of(user3.getNickName(),LocalDateTime.now().toString());
        mypageUserInfos.add(myPageUserInfo);
        mypageUserInfos.add(myPageUserInfo2);

        when(followMyPageUserInfoService.findFolloweeUserByFollowerId(user2.getUserId())).thenReturn(mypageUserInfos);

        assertAll(
                () -> assertThat(mypageUserInfos.get(0).getNickName()).isEqualTo(user.getNickName()),
                () -> assertThat(mypageUserInfos.get(1).getNickName()).isEqualTo(user3.getNickName())
        );
    }

    @Test
    @DisplayName("view 단에 보여줄 팔로잉 정보")
    void viewFolloweeimpleUserInfo() {

    }
}