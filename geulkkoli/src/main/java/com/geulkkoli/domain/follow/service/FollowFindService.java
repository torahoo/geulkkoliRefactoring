package com.geulkkoli.domain.follow.service;

import com.geulkkoli.domain.follow.Follow;
import com.geulkkoli.domain.follow.FollowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FollowFindService {
    private final FollowRepository followRepository;

    public FollowFindService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @Transactional(readOnly = true)
    public List<Follow> findAllFollowerByFolloweeId(Long followeeId) {
        return followRepository.findFollowEntitiesByFollowee_UserId(followeeId);
    }

    @Transactional(readOnly = true)
    public Integer countFollowerByFolloweeId(Long followeeId) {
        return followRepository.countByFollowee_UserId(followeeId);
    }

    @Transactional(readOnly = true)
    public Integer countFolloweeByFollowerId(Long followerId) {
        return followRepository.countByFollower_UserId(followerId);
    }

    @Transactional(readOnly = true)
    public List<Follow> findAllFolloweeByFollowerId(Long followerId) {
        return followRepository.findFollowEntitiesByFollower_UserId(followerId);
    }

}
