package com.geulkkoli.domain.follow.service;

import com.geulkkoli.domain.follow.FollowEntity;
import com.geulkkoli.domain.follow.FollowRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    private final UserService userService;

    public FollowService(FollowRepository followRepository, UserService userService) {
        this.followRepository = followRepository;
        this.userService = userService;
    }

    public FollowEntity follow(Long followeeId, Long followerId) {
        User followee = userService.findById(followeeId);
        User follower = userService.findById(followerId);

        return followRepository.save(FollowEntity.of(followee, follower));
    }

    @Transactional(readOnly = true)
    public List<FollowEntity> findAllFollowerByFolloweeId(Long followeeId) {

        return followRepository.findFollowEntitiesByFollowee_UserId(followeeId);
    }

    @Transactional
    public void deleteByFolloweeIdAndFollowerId(Long followeeId, Long followerId) {
        try {
            FollowEntity followEntity = followRepository.findByFollowee_UserIdAndFollower_UserId(followeeId, followerId);
            followRepository.delete(followEntity);
        } catch (Exception e) {
            throw new IllegalArgumentException("존재하지 않는 팔로우입니다.");
        }

    }
}
