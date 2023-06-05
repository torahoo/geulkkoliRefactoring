package com.geulkkoli.domain.follow;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public FollowEntity save(Long followeeId, Long followerId) {
        User followee = userRepository.findById(followeeId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팔로우 대상입니다."));
        User follower = userRepository.findById(followerId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팔로워입니다."));

        return followRepository.save(FollowEntity.of(followee, follower));
    }

    @Transactional(readOnly = true)
    public FollowEntity findByFollowerId(Long followerId) {
        return followRepository.findByFollower_UserId(followerId);
    }

    public void deleteByFolloweeIdAndFollowerId(Long followeeId, Long followerId) {
        try {
            FollowEntity followEntity = followRepository.findByFollowee_UserIdAndFollower_UserId(followeeId, followerId);
            followRepository.delete(followEntity);
        } catch (Exception e) {
            throw new IllegalArgumentException("존재하지 않는 팔로우입니다.");
        }

    }
}
