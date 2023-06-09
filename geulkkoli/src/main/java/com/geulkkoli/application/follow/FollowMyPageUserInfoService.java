package com.geulkkoli.application.follow;

import com.geulkkoli.domain.follow.service.FollowFindService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowMyPageUserInfoService {
    private final FollowFindService followFindService;

    public FollowMyPageUserInfoService(FollowFindService followFindService) {
        this.followFindService = followFindService;
    }

    public List<MyPageUserInfo> followUserInfosByFolloweeId(Long followeeId) {
        return followFindService.findAllFollowerByFolloweeId(followeeId)
                .stream()
                .map(followEntity -> MyPageUserInfo.of(followEntity.followeeNickName(), followEntity.getCreatedAt()))
                .collect(Collectors.toUnmodifiableList());
    }
}
