package com.geulkkoli.web.mypage;

import com.geulkkoli.application.follow.FollowInfo;
import com.geulkkoli.application.follow.FollowInfos;
import com.geulkkoli.application.user.CustomAuthenticationPrinciple;
import com.geulkkoli.domain.follow.service.FollowFindService;
import com.geulkkoli.domain.social.SocialInfoService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserFindService;
import com.geulkkoli.web.mypage.dto.ConnectedSocialInfos;
import com.geulkkoli.web.mypage.dto.FollowsCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/my-page")
@Slf4j
public class MyPageController {

    private final SocialInfoService socialInfoService;
    private final UserFindService userFindService;
    private final FollowFindService followFindService;


    @GetMapping()
    public ModelAndView getMyPage(@AuthenticationPrincipal CustomAuthenticationPrinciple loginUser) {
        ConnectedSocialInfos connectedInfos = socialInfoService.findConnectedInfos(loginUser.getUsername());
        User user = userFindService.findUserByEmail(loginUser.getUsername());
        log.info("user: {}", user.getUserId());
        Integer followee = followFindService.countFolloweeByFollowerId(user.getUserId());
        Integer follower = followFindService.countFollowerByFolloweeId(user.getUserId());
        FollowsCount followsCount = FollowsCount.of(followee, follower);
        ModelAndView modelAndView = new ModelAndView("mypage/mypage", "connectedInfos", connectedInfos);
        modelAndView.addObject("followsCount", followsCount);
        return modelAndView;
    }

    @GetMapping("/followees")
    public ModelAndView getFollowees(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @AuthenticationPrincipal CustomAuthenticationPrinciple loginUser) {
        User user = userFindService.findUserByEmail(loginUser.getUsername());
        Integer followee = followFindService.countFolloweeByFollowerId(user.getUserId());
        FollowInfos followeeUserInfos = followFindService.findSomeFolloweeByFollowerId(user.getUserId(), null, pageable);
        ModelAndView modelAndView = new ModelAndView("mypage/followdetail", "followers", followeeUserInfos);
        modelAndView.addObject("allCount", followee);

        return modelAndView;
    }

    @GetMapping("/followers")
    public ModelAndView getFollowers(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @AuthenticationPrincipal CustomAuthenticationPrinciple loginUser) {
        User user = userFindService.findUserByEmail(loginUser.getUsername());
        Integer follower = followFindService.countFollowerByFolloweeId(user.getUserId());
        List<FollowInfo> followerUserInfos = followFindService.findSomeFollowerByFolloweeId(user.getUserId(), null, pageable);
        FollowInfos followInfos = FollowInfos.of(followerUserInfos);
        List<Long> userIdByFollowedEachOther = followFindService.findUserIdByFollowedEachOther(followInfos.userIds(), user.getUserId(), pageable.getPageSize());
        followInfos.checkSubscribe(userIdByFollowedEachOther);

        log.info("followInfos: {}", followInfos.getFollowInfos());

        ModelAndView modelAndView = new ModelAndView("mypage/followerdetail", "followers", followInfos);
        modelAndView.addObject("allCount", follower);

        return modelAndView;
    }
}
