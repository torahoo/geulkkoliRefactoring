package com.geulkkoli.domain.follow.service;

import com.geulkkoli.domain.follow.Follow;
import com.geulkkoli.domain.follow.FollowRepository;
import com.geulkkoli.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FollowFindService {
    private final FollowRepository followRepository;

    public FollowFindService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }


    public List<Follow> findAllFollowerByFolloweeId(Long followeeId) {
        return followRepository.findFollowEntitiesByFollowee_UserId(followeeId);
    }

    public Integer countFollowerByFolloweeId(Long followeeId) {
        return followRepository.countByFollowee_UserId(followeeId);
    }

    public Integer countFolloweeByFollowerId(Long followerId) {
        return followRepository.countByFollower_UserId(followerId);
    }

    public List<Follow> findAllFolloweeByFollowerId(Long followerId) {
        return followRepository.findFollowEntitiesByFollower_UserId(followerId);
    }

    public Boolean checkFollow(User loggingUser, User authorUser) {
        return followRepository.existsByFollowee_UserIdAndFollower_UserId(authorUser.getUserId(), loggingUser.getUserId());
    }
}
