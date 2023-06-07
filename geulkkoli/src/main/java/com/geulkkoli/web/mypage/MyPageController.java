package com.geulkkoli.web.mypage;

import com.geulkkoli.application.follow.FollowMyPageUserInfoService;
import com.geulkkoli.application.user.CustomAuthenticationPrinciple;
import com.geulkkoli.domain.follow.service.FollowService;
import com.geulkkoli.domain.social.SocialInfoService;
import com.geulkkoli.web.mypage.dto.ConnectedSocialInfos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/my-page")
public class MyPageController {

    @Autowired
    private SocialInfoService socialInfoService;


    @GetMapping()
    public ModelAndView getMyPage(@AuthenticationPrincipal CustomAuthenticationPrinciple loginUser) {
        ConnectedSocialInfos connectedInfos = socialInfoService.findConnectedInfos(loginUser.getUsername());

        return new ModelAndView("mypage/mypage", "connectedInfos", connectedInfos);
    }
}
