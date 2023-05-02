package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.follow.Follow;
import com.geulkkoli.domain.follow.FollowRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.FollowerFormDto;
import com.geulkkoli.web.user.dto.UserInfoEditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FollowRepository followRepository;

    public boolean isEmailDuplicate(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isNickNameDuplicate(String nickName) {
        return userRepository.findByNickName(nickName).isPresent();
    }

    public boolean isPhoneNoDuplicate(String phoneNo) {
        return userRepository.findByPhoneNo(phoneNo).isPresent();
    }


    public void edit(Long id, UserInfoEditDto userInfoEditDto) {
        userRepository.edit(id, userInfoEditDto);
    }

    // password Encoder를 사용하는 곳을 usersecurityservice로 옮겼기 때문에 이 메서드는 필요 없어져 지웁니다.

    public void delete(User user) {
        userRepository.deleteById(user.getUserId());
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user found id matches:" + id));
    }

    public User findByNickName(String nickName) {
        return userRepository.findByNickName(nickName)
                .orElseThrow(() -> new NoSuchElementException("No user found nickname matches:" + nickName));
    }

    public User findeUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("팔로잉 유저가 존재하지 않는다."));
    }

    public List<FollowerFormDto> findAllFollowedUser() {
        List<Follow> allUser = followRepository.findAll();
        List<FollowerFormDto> followerFormDtos = new ArrayList<>();

        for (Follow follow : allUser) {
            User user = follow.getFollowerId();
            followerFormDtos.add(FollowerFormDto.toDTO(user));
        }

        return followerFormDtos;
    }

    public List<FollowerFormDto> findAllFolloweeUser() {
        List<Follow> allUser = followRepository.findAll();
        List<FollowerFormDto> followerFormDtos = new ArrayList<>();

        for( Follow follow : allUser) {
            User user = follow.getFolloweeId();
            followerFormDtos.add(FollowerFormDto.toDTO(user));
        }
        return followerFormDtos;
    }
}
