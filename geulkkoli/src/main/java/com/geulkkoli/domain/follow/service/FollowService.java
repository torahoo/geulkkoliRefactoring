package com.geulkkoli.domain.follow.service;

import com.geulkkoli.domain.follow.Follow;
import com.geulkkoli.domain.follow.FollowRepository;
import com.geulkkoli.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;


    public Follow follow(User followee, User follower) {
        Follow follow = follower.follow(followee);
        return followRepository.save(follow);
    }

    public Follow unfollow(User followee, User follower) {
        Follow unFollowResult = follower.unfollow(followee);
        followRepository.delete(unFollowResult);
        return unFollowResult;
    }

    public void deleteByFolloweeIdAndFollowerId(Long followeeId, Long followerId) {
        try {
            Follow followEntity = followRepository.findByFollowee_UserIdAndFollower_UserId(followeeId, followerId);
            followRepository.delete(followEntity);
        } catch (Exception e) {
            throw new IllegalArgumentException("존재하지 않는 팔로우입니다.");
        }

    }
}
