package com.geulkkoli.domain.follow;

import com.geulkkoli.domain.follow.service.FollowService;
import com.geulkkoli.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

class FollowServiceTest {
    private FollowRepository followRepository;
    private FollowService followService;


    @BeforeEach
    void setUp() {
        followRepository = Mockito.mock(FollowRepository.class);
        followService = new FollowService(followRepository);
    }

    @Test
    void follow() {
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

        ReflectionTestUtils.setField(user, "userId", 1L);
        ReflectionTestUtils.setField(user2, "userId", 2L);


        Follow followEntity = Follow.of(user, user2);

        when(followService.follow(user, user2)).thenReturn(followEntity);

        assertAll(() -> {
            assertThat(followEntity.getFollowee()).isEqualTo(user);
            assertThat(followEntity.getFollower()).isEqualTo(user2);
            assertThat(followEntity.getFollowee().getNickName()).isEqualTo("nickName");
            assertThat(followEntity.getFollower().getNickName()).isEqualTo("nickName2");
        });
    }


    @Test
    void delete() {
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


        Follow followEntity = Follow.of(user, user2);

        given(followRepository.findByFolloweeUserIdAndFollowerUserId(1L, 2L)).willReturn(followEntity);

        followService.deleteByFolloweeIdAndFollowerId(1L, 2L);

        then(followRepository).should().delete(followEntity);

    }
}