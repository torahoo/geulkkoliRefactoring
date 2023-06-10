package com.geulkkoli.domain.follow;

import com.geulkkoli.application.follow.FollowInfo;

import java.util.List;

public interface FollowRepositoryCustom {
    List<FollowInfo> findFollowEntitiesByFolloweeUserId(Long followeeId, Long lastFollowId, int pageSize);

    List<FollowInfo> findFollowEntitiesByFollowerUserId(Long followerId, Long lastFollowId, int pageSize);
}
