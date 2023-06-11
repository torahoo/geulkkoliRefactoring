package com.geulkkoli.domain.follow;

import com.geulkkoli.application.follow.FollowInfo;

import java.util.List;

public interface FollowRepositoryCustom {
    List<FollowInfo> findFollowersByFolloweeUserId(Long followeeId, Long lastFollowId, int pageSize);

    List<FollowInfo> findFolloweesByFollowerUserId(Long followerId, Long lastFollowId, int pageSize);
}
