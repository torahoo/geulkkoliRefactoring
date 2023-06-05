package com.geulkkoli.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    FollowEntity findByFollower_UserId(Long userId);

    FollowEntity findByFollowee_UserIdAndFollower_UserId(Long followeeId, Long followerId);
}
