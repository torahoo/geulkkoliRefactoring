package com.geulkkoli.domain.follow;

import com.geulkkoli.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FollowEntityTest {
    @Test
    void dummy() {
        FollowEntity followEntity = new FollowEntity(1L,User.builder().userName("followee").build(), User.builder().userName("follower").build());
        FollowEntity followEntity1 = new FollowEntity(2L,User.builder().userName("followee").build(), User.builder().userName("follower1").build());

        assertThat(followEntity).isEqualTo(followEntity1);
    }
}
