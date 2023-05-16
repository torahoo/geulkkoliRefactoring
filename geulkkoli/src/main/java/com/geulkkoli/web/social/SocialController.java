package com.geulkkoli.web.social;

import com.geulkkoli.application.security.AccountStatus;
import com.geulkkoli.application.user.AuthUser;
import com.geulkkoli.application.user.UserModelDto;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/social")
@Slf4j
public class SocialController {
    @Autowired
    private UserService userService;

    @GetMapping("/oauth2/signup")
    public ModelAndView moveSignUpPage(@AuthenticationPrincipal AuthUser authUser, ModelAndView modelAndView) {
        log.info("소셜 로그인 회원의 회원 정보 기입");
        SocialSignUpDto socialSignUpDto = SocialSignUpDto.builder()
                .email(authUser.getUsername())
                .nickName(authUser.getNickName())
                .phoneNo(authUser.getPhoneNo())
                .verifyPassword(authUser.getPassword())
                .gender(authUser.getGender())
                .userName(authUser.getName())
                .password(authUser.getPassword())
                .build();
        SecurityContextHolder.clearContext();

        modelAndView.addObject("signUpDto", socialSignUpDto);
        modelAndView.setViewName("social/oauth2/signup");
        return modelAndView;
    }

    @PostMapping("/oauth2/signup")
    public ModelAndView signUp(@ModelAttribute("signUpDto") SocialSignUpDto signUpDtoUpDto, BindingResult bindingResult, ModelAndView modelAndView) {
        log.info("소셜 로그인 회원의 회원 정보 기입");
        modelAndView.setViewName("social/oauth2/signup");
        if (userService.isNickNameDuplicate(signUpDtoUpDto.getNickName())) {
            bindingResult.rejectValue("nickName", "Duple.nickName");
        }

        if (userService.isPhoneNoDuplicate(signUpDtoUpDto.getPhoneNo())) {
            bindingResult.rejectValue("phoneNo", "Duple.phoneNo");
            return modelAndView;
        }

        if (bindingResult.hasErrors()) {
            return modelAndView;
        }
        User user = userService.signUp(signUpDtoUpDto);
        UserModelDto dto = UserModelDto.toDto(user);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.authority()));
        AuthUser from = AuthUser.from(dto, authorities, AccountStatus.ACTIVE);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(from, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        modelAndView.setViewName("/home");
        return modelAndView;
    }
}
