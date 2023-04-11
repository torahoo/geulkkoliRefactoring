package com.geulkkoli.web.user;

import com.geulkkoli.application.user.UserSecurityService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    public static final String LOGIN_FORM = "user/loginForm";
    public static final String JOIN_FORM = "user/joinForm";
    public static final String REDIRECT_INDEX = "redirect:/";
    private final UserService userService;
    private final UserSecurityService customUserDetailsService;

    @GetMapping("/loginPage")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return LOGIN_FORM;
    }

    @GetMapping("/login/error")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult) {
        log.info("email {} , password {}", form.getEmail(), form.getPassword());
        if (bindingResult.hasErrors()) {
            return LOGIN_FORM;
        }
        bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
        return LOGIN_FORM;

    }

    //join
    @GetMapping("/join")
    public String joinForm(@ModelAttribute JoinForm form) {
        return JOIN_FORM;
    }

    @PostMapping("/join")
    public String userJoin(@Validated @ModelAttribute("joinForm") JoinForm form, BindingResult bindingResult, Model model) {
        log.info("join Method={}", this);
        if (bindingResult.hasErrors()) {
            return JOIN_FORM;
        }

        if (userService.isEmailDuplicate(form.getEmail())) {
            bindingResult.rejectValue("email", "Duple.joinForm.email");
            return JOIN_FORM;
        }

        if (userService.isNickNameDuplicate(form.getNickName())) {
            bindingResult.rejectValue("nickName", "Duple.joinForm.nickName");
            return JOIN_FORM;

        }

        if (userService.isPhoneNoDuplicate(form.getPhoneNo())) {
            bindingResult.rejectValue("phoneNo", "Duple.joinForm.phoneNo");
            return JOIN_FORM;

        }

        if (!form.getPassword().equals(form.getVerifyPassword())) {
            bindingResult.rejectValue("verifyPassword", "Duple.joinForm.verifyPassword");
            return JOIN_FORM;

        }

        if (!bindingResult.hasErrors()) {
            userService.join(form);
        }

        log.info("model = {}", model);
        log.info("form {}", form);

        return REDIRECT_INDEX;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @PostMapping("/memberDelete")
    public String memberDelete(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            userService.delete((User) session.getAttribute(SessionConst.LOGIN_USER));
            session.invalidate();
        }
        return "redirect:/";
    }
}
