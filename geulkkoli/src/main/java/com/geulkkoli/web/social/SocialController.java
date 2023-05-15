package com.geulkkoli.web.social;

import com.geulkkoli.application.user.AuthUser;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.user.dto.JoinFormDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/social")
@Slf4j
public class SocialController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/oauth2/signup")
    public ModelAndView signUp(){
        log.info("소셜 로그인 회원의 회원 정보 기입");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("social/oauth2/signup");
        return modelAndView;
    }

    @PostMapping("/oauth2/signup")
    public ModelAndView signUp(SocialSignUpDto socialSignUpDto, @AuthenticationPrincipal AuthUser authUser){
        log.info("소셜 로그인 회원의 회원 정보 기입");
        User user = User.builder()
                .email(authUser.getUsername())
                .password(socialSignUpDto.getPassword())
                .userName(authUser.getUserRealName())
                .nickName(socialSignUpDto.getNickName())
                .phoneNo(socialSignUpDto.getPhoneNumber())
                .gender(authUser.getGender())
                .build();
        userRepository.save(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index");
        return modelAndView;
    }
}
