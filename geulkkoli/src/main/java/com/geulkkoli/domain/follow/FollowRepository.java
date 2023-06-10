package com.geulkkoli.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom{

    Integer countByFolloweeUserId(Long followeeId);

    Integer countByFollowerUserId(Long followerId);

    List<Follow> findFollowEntitiesByFolloweeUserId(Long followeeId);

    Follow findByFolloweeUserIdAndFollowerUserId(Long followeeId, Long followerId);

    Boolean existsByFolloweeUserIdAndFollowerUserId(Long followeeId, Long followerId);
}
