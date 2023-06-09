package com.geulkkoli.application.follow;

import com.geulkkoli.domain.follow.service.FollowFindService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowMyPageUserInfoService {
    private final FollowFindService followFindService;

    public FollowMyPageUserInfoService(FollowFindService followFindService) {
        this.followFindService = followFindService;
    }

    public List<MyPageUserInfo> findFolloweeUserByFollowerId(Long followerId, Pageable pageable) {
        return followFindService.findAllFolloweeByFollowerId(followerId, pageable)
                .stream()
                .map(followEntity -> MyPageUserInfo.of(followEntity.followeeNickName(), followEntity.getCreatedAt()))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<MyPageUserInfo> followeeUserInfosByFolloweeId(Long followeeId, Pageable pageable) {
        return followFindService.findAllFollowerByFolloweeId(followeeId, pageable)
                .stream()
                .map(followEntity -> MyPageUserInfo.of(followEntity.followeeNickName(), followEntity.getCreatedAt()))
                .collect(Collectors.toUnmodifiableList());
    }
}
