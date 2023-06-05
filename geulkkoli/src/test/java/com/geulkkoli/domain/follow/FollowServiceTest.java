package com.geulkkoli.domain.follow;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class FollowServiceTest {
    private FollowRepository followRepository;
    private UserRepository userRepository;

    private FollowService followService;


    @BeforeEach
    void setUp() {
        followRepository = Mockito.mock(FollowRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        followService = new FollowService(followRepository, userRepository);
    }

    @Test
    void save() {
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

        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userRepository.findById(2L)).willReturn(Optional.of(user2));

        FollowEntity followEntity = FollowEntity.of(user, user2);

        when(followService.save(1L, 2L)).thenReturn(followEntity);

        assertAll(() -> {
            assertThat(followEntity.getFollowee()).isEqualTo(user);
            assertThat(followEntity.getFollower()).isEqualTo(user2);
            assertThat(followEntity.getFollowee().getNickName()).isEqualTo("nickName");
            assertThat(followEntity.getFollower().getNickName()).isEqualTo("nickName2");
        });
    }

    @Test
    void findByFollower_UserId() {
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

        FollowEntity followEntity = FollowEntity.of(user, user2);
        given(followRepository.findByFollower_UserId(2L)).willReturn(followEntity);

        when(followService.findByFollowerId(2L)).thenReturn(followEntity);

        FollowEntity byFollowerId = followService.findByFollowerId(2L);

        assertAll(() -> {
            assertThat(byFollowerId.getFollowee()).isEqualTo(user);
            assertThat(byFollowerId.getFollower()).isEqualTo(user2);
            assertThat(byFollowerId.getFollowee().getNickName()).isEqualTo("nickName");
            assertThat(byFollowerId.getFollower().getNickName()).isEqualTo("nickName2");
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


        FollowEntity followEntity = FollowEntity.of(user, user2);

        given(followRepository.findByFollowee_UserIdAndFollower_UserId(1L, 2L)).willReturn(followEntity);

        followService.deleteByFolloweeIdAndFollowerId(1L, 2L);

        then(followRepository).should().delete(followEntity);

    }
}