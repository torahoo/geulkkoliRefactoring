package com.geulkkoli.web.mypage;

import com.geulkkoli.application.follow.FollowMyPageUserInfoService;
import com.geulkkoli.application.follow.MyPageUserInfo;
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
    private final FollowMyPageUserInfoService followMyPageUserInfoService;


    @GetMapping()
    public ModelAndView getMyPage(@AuthenticationPrincipal CustomAuthenticationPrinciple loginUser) {
        ConnectedSocialInfos connectedInfos = socialInfoService.findConnectedInfos(loginUser.getUsername());
        User user = userFindService.findUserByEmail(loginUser.getUsername());
        Integer followee = followFindService.countFolloweeByFollowerId(user.getUserId());
        Integer follower = followFindService.countFollowerByFolloweeId(user.getUserId());
        FollowsCount followsCount = FollowsCount.of(followee, follower);
        ModelAndView modelAndView = new ModelAndView("mypage/mypage", "connectedInfos", connectedInfos);
        modelAndView.addObject("followsCount", followsCount);
        return modelAndView;
    }

    @GetMapping("/followees")
    public ModelAndView getFollowees(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @AuthenticationPrincipal CustomAuthenticationPrinciple loginUser) {
        User user = userFindService.findUserByEmail(loginUser.getUsername());
        List<MyPageUserInfo> followeeUserInfos = followMyPageUserInfoService.findFolloweeUserByFollowerId(user.getUserId(), pageable);
        log.info("followeeUserInfo: {}", followeeUserInfos.size());
        return new ModelAndView("mypage/followdetail", "followees", followeeUserInfos);
    }
}
