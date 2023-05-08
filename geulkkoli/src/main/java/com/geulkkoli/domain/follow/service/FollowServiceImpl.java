package com.geulkkoli.domain.follow.service;

import com.geulkkoli.domain.follow.Follow;
import com.geulkkoli.domain.follow.FollowRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.FolloweeDto;
import com.geulkkoli.web.user.FollowerFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;

    private final UserRepository userRepository;


    public List<FollowerFormDto> findAllFollowedUser() {
        List<Follow> allUser = followRepository.findAll();
        List<FollowerFormDto> followerFormDtos = new ArrayList<>();

        for (Follow follow : allUser) {
            User user = follow.getFollowerId();
            followerFormDtos.add(FollowerFormDto.toDTO(user));
        }

        return followerFormDtos;
    }

    public List<FolloweeDto> findAllFolloweeUser() {
        List<Follow> allUser = followRepository.findAll();
        List<FolloweeDto> followeeDtos = new ArrayList<>();

        for( Follow follow : allUser) {
            User user = follow.getFolloweeId();
            followeeDtos.remove(FollowerFormDto.toDTO(user));
        }
        return followeeDtos;
    }
}
