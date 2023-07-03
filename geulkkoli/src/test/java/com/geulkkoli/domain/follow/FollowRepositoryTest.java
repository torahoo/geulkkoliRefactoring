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

    }

    @Test
    void findFollowEntitiesByFollowerUserId() {
    }
}