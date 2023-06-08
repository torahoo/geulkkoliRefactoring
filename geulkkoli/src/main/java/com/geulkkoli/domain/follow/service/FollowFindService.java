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
        return followRepository.findFollowEntitiesByFolloweeUserId(followeeId);
    }

    public Integer countFollowerByFolloweeId(Long followeeId) {
        return followRepository.countByFollowee_UserId(followeeId);
    }

    public Integer countFolloweeByFollowerId(Long followerId) {
        return followRepository.countByFollowerUserId(followerId);
    }

    public List<Follow> findAllFolloweeByFollowerId(Long followerId) {
        return followRepository.findFollowEntitiesByFollowerUserId(followerId);
    }

    public Boolean checkFollow(User loggingUser, User authorUser) {
        return followRepository.existsByFolloweeUserIdAndFollowerUserId(authorUser.getUserId(), loggingUser.getUserId());
    }
}
