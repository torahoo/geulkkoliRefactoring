package com.geulkkoli.domain.follow;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class FollowRepositoryTest {
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional(readOnly = true)
    void findFollowEntitiesByFolloweeUserId() {
        User user = User.builder()
                .email("test@gmil.com")
                .userName("test")
                .password("test")
                .gender("male")
                .phoneNo("010-1234-1234")
                .nickName("test")
                .build();

        User user1 = User.builder()
                .email("test1@gmil.com")
                .userName("test1")
                .password("test1")
                .gender("male")
                .phoneNo("010-1244-1234")
                .nickName("test1")
                .build();
        userRepository.save(user);
        userRepository.save(user1);
        followRepository.save(user.follow(user1));

        Slice<Follow> followers = followRepository.findFollowEntitiesByFolloweeUserId(user1.getUserId(), PageRequest.of(0, 3));

        System.out.println(followers);
    }

    @Test
    void findFollowEntitiesByFollowerUserId() {
    }
}