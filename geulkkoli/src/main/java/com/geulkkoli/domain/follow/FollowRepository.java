package com.geulkkoli.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    Integer countByFollowee_UserId(Long followeeId);

    Integer countByFollower_UserId(Long followerId);

    List<FollowEntity> findFollowEntitiesByFollowee_UserId(Long followeeId);

    List<FollowEntity> findFollowEntitiesByFollower_UserId(Long followerId);

    FollowEntity findByFollowee_UserIdAndFollower_UserId(Long followeeId, Long followerId);
}
