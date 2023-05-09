package com.geulkkoli.domain.follow;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@DataJpaTest
@Transactional
public class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    @Transactional
    @DisplayName("팔로우 기능 테스트")
    void follow() {
        //given
        User follower = User.builder()
                .userName("김")
                .nickName("김김")
                .gender("M")
                .password("XXXX")
                .phoneNo("01012345678")
                .email("ttt@gmail.com")
                .build();

        User followee = User.builder()
                .userName("호")
                .nickName("러라")
                .gender("M")
                .password("XXXX")
                .phoneNo("01099998856")
                .email("ttt22265@gmail.com")
                .build();

        User user1 = userRepository.save(follower);
        User user2 = userRepository.save(followee);
        Follow follow = Follow.of(user1, user2);

        Follow save = followRepository.save(follow);

        //then
        Optional<Follow> followUser = followRepository.findBySelectUserId(user2.getUserId());
        System.out.println("followUser = " + followUser);

        assertAll(() -> assertThat(followUser).hasValue(save) ,
                () -> assertThat(followUser).get().hasFieldOrPropertyWithValue("followerId", user1),
                () -> assertThat(followUser).get().hasFieldOrPropertyWithValue("followeeId", user2));
    }

    @Test
    @DisplayName("팔로우 취소 기능 테스트")
    @Transactional
    void followCancel() {
        //given
        User follower = User.builder()
                .userName("김")
                .nickName("김김")
                .gender("M")
                .password("XXXX")
                .phoneNo("01012345678")
                .email("ttt@gmail.com")
                .build();

        User followee = User.builder()
                .userName("호")
                .nickName("러라")
                .gender("M")
                .password("XXXX")
                .phoneNo("01099998856")
                .email("ttt22265@gmail.com")
                .build();

        User user1 = userRepository.save(follower);
        User user2 = userRepository.save(followee);
        Follow follow = Follow.of(user1, user2);

        Follow save = followRepository.save(follow);

        //then

//        Optional<Follow> followUser = followRepository.findBySelectUserId(user2.getUserId());
//        System.out.println("followUser = " + followUser);
//
//        assertAll(() -> assertThat(followUser).hasValue(save) ,
//                () -> assertThat(followUser).get().hasFieldOrPropertyWithValue("followerId", user1),
//                () -> assertThat(followUser).get().hasFieldOrPropertyWithValue("followeeId", user2));

        Optional<Follow> followeeUser = followRepository.findByDeleteUserId(user2.getUserId());
        System.out.println("followeeUser = " + followeeUser);




    }
}
