package com.geulkkoli.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Integer countByFollowee_UserId(Long followeeId);

    Integer countByFollower_UserId(Long followerId);

    List<Follow> findFollowEntitiesByFollowee_UserId(Long followeeId);

    List<Follow> findFollowEntitiesByFollower_UserId(Long followerId);

    Follow findByFollowee_UserIdAndFollower_UserId(Long followeeId, Long followerId);
}
