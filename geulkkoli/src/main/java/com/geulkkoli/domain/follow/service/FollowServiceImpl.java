package com.geulkkoli.domain.follow.service;

import com.geulkkoli.domain.follow.Follow;
import com.geulkkoli.domain.follow.FollowRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.web.myPage.dto.follow.FolloweeFormDto;
import com.geulkkoli.web.myPage.dto.follow.FollowerFormDto;
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


    public List<FollowerFormDto> findAllFollowedUser() {
        List<Follow> allUser = followRepository.findAll();
        List<FollowerFormDto> followerFormDtos = new ArrayList<>();

        for (Follow follow : allUser) {
            User user = follow.getFollowerId();
            followerFormDtos.add(FollowerFormDto.toDTO(user));
        }

        return followerFormDtos;
    }

    public List<FolloweeFormDto> findAllFolloweeUser() {
        List<Follow> allUser = followRepository.findAll();
        List<FolloweeFormDto> followeeFormDtos = new ArrayList<>();

        for( Follow follow : allUser) {
            User user = follow.getFolloweeId();
            followeeFormDtos.remove(FollowerFormDto.toDTO(user));
        }
        return followeeFormDtos;
    }
}
