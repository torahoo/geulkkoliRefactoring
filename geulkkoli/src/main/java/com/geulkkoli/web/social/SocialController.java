package com.geulkkoli.web.social;

import com.geulkkoli.application.security.AccountStatus;
import com.geulkkoli.application.user.AuthUser;
import com.geulkkoli.application.user.UserModelDto;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/social")
@Slf4j
public class SocialController {
    @Autowired
    private UserService userService;

    @GetMapping("/oauth2/signup")
    public ModelAndView signUp(@ModelAttribute("signUpDto") SocialSignUpDto signUpDtoUpDto, @AuthenticationPrincipal AuthUser authUser) {
        log.info("소셜 로그인 회원의 회원 정보 기입");
        log.info("authUser : {}", authUser.getUsername());
        signUpDtoUpDto.setEmail(authUser.getUsername());
        signUpDtoUpDto.setGender(authUser.getGender());
        signUpDtoUpDto.setNickName(authUser.getNickName());
        signUpDtoUpDto.setPassword(authUser.getPassword());
        signUpDtoUpDto.setPhoneNo(authUser.getPhoneNo());
        signUpDtoUpDto.setUserName(authUser.getUserRealName());
        signUpDtoUpDto.setVerifyPassword(authUser.getPassword());
        SecurityContextHolder.clearContext();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("social/oauth2/signup");
        return modelAndView;
    }

    @PostMapping("/oauth2/signup")
    public ModelAndView signUp(@ModelAttribute("signUpDto") SocialSignUpDto signUpDtoUpDto, BindingResult bindingResult) {
        log.info("소셜 로그인 회원의 회원 정보 기입");
        User user = userService.signUp(signUpDtoUpDto);
        ModelAndView modelAndView = new ModelAndView();
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

        UserModelDto dto = UserModelDto.toDto(user);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRole().getRoleName()));
        AuthUser from = AuthUser.from(dto, authorities, AccountStatus.ACTIVE);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(from, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        modelAndView.setViewName("/home");
        return modelAndView;
    }
}
