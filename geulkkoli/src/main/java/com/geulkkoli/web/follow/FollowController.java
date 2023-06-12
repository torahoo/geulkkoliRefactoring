package com.geulkkoli.web.follow;

import com.geulkkoli.application.follow.FollowMyPageUserInfoService;
import com.geulkkoli.application.user.CustomAuthenticationPrinciple;
import com.geulkkoli.domain.follow.service.FollowFindService;
import com.geulkkoli.domain.follow.service.FollowService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FollowController {
    private final FollowService followService;

    private final FollowMyPageUserInfoService followMyPageUserInfoService;

    private final FollowFindService followFindService;

    private final UserFindService userFindService;

    @GetMapping("follow/{email}")
    public ResponseEntity<?> followUser(@PathVariable("email") String email, @AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {
        userFindService.findByEmail(email).ifPresent(followee -> {
            log.info("followee: {}", followee);
            User follower = userFindService.findById(Long.parseLong(authUser.getUserId()));
            followService.follow(followee, follower);
        });

        return ResponseEntity.ok().build();
    }

    @GetMapping("unfollow/{email}")
    public ResponseEntity<?> unfollowUser(@PathVariable("email") String email, @AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {
        userFindService.findByEmail(email).ifPresent(followee -> {
            User follower = userFindService.findById(Long.parseLong(authUser.getUserId()));
            followService.unfollow(followee, follower);
        });

        return ResponseEntity.ok().build();
    }
}
