package com.geulkkoli.web.mypage;

import com.geulkkoli.application.follow.FollowMyPageUserInfoService;
import com.geulkkoli.application.user.CustomAuthenticationPrinciple;
import com.geulkkoli.domain.follow.Follow;
import com.geulkkoli.domain.follow.service.FollowFindService;
import com.geulkkoli.domain.follow.service.FollowService;
import com.geulkkoli.domain.social.SocialInfoService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserFindService;
import com.geulkkoli.web.mypage.dto.ConnectedSocialInfos;
import com.geulkkoli.web.mypage.dto.FollowsCount;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/my-page")

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
}
