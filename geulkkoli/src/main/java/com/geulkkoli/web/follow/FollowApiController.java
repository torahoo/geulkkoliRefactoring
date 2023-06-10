package com.geulkkoli.web.follow;

import com.geulkkoli.application.follow.FollowInfo;
import com.geulkkoli.application.user.CustomAuthenticationPrinciple;
import com.geulkkoli.domain.follow.service.FollowFindService;
import com.geulkkoli.domain.follow.service.FollowService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FollowApiController {
    private final FollowService followService;


    private final FollowFindService followFindService;

    private final UserFindService userFindService;

    @GetMapping("api/follow/{email}")
    public ResponseEntity<?> followUser(@PathVariable("email") String email, @AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {
        Optional<User> byEmail = userFindService.findByEmail(email);
        if (byEmail.isPresent()) {
            User followee = byEmail.get();
            User follower = userFindService.findByEmail(authUser.getUsername()).get();
            followService.follow(followee, follower);
            return ResponseEntity.ok().build();
        }
        throw new NoSuchElementException("해당 이메일을 가진 유저가 없습니다.");
    }

    @GetMapping("api/unfollow/{email}")
    public ResponseEntity<?> unfollowUser(@PathVariable("email") String email, @AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {
        userFindService.findByEmail(email).ifPresent(followee -> {
            User follower = userFindService.findById(Long.parseLong(authUser.getUserId()));
            followService.unfollow(followee, follower);
        });

        return ResponseEntity.ok().build();
    }

    @GetMapping("api/followees/{lastFollowId}")
    public ResponseEntity<List<FollowInfo>> getFollowees(@PathVariable String lastFollowId, @PageableDefault Pageable pageable, @AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {
        Optional<User> byEmail = userFindService.findByEmail(authUser.getUserName());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            return ResponseEntity.ok().body(followFindService.findAllFolloweeByFollowerId(user.getUserId(), Long.parseLong(lastFollowId), pageable));
        }
        return  ResponseEntity.status(404).build();
    }
}
