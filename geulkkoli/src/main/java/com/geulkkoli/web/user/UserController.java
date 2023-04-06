package com.geulkkoli.web.user;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.JoinService;
import com.geulkkoli.domain.user.service.LoginService;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    public static final String LOGIN_FORM = "user/loginForm";
    public static final String JOIN_FORM = "user/joinForm";
    private final LoginService loginService;
    private final JoinService joinService;


    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return LOGIN_FORM;
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
        log.info("email {} , password {}", form.getEmail(), form.getPassword());
        if (bindingResult.hasErrors()) {
            return LOGIN_FORM;
        }

        Optional<User> loginUser = loginService.login(form.getEmail(), form.getPassword());

        if (loginUser.isEmpty()) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return LOGIN_FORM;
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginUser.get());
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
        if (bindingResult.hasErrors()){
            return JOIN_FORM;
        }

        if (joinService.isEmailDuplicate(form.getEmail())) {
            bindingResult.rejectValue("email", "Duple.joinForm.email");
            return JOIN_FORM;
        }

        if (joinService.isNickNameDuplicate(form.getNickName())) {
            bindingResult.rejectValue("nickName", "Duple.joinForm.nickName");
            return JOIN_FORM;

        }

        if (joinService.isPhoneNoDuplicate(form.getPhoneNo())) {
            bindingResult.rejectValue("phoneNo", "Duple.joinForm.phoneNo");
            return JOIN_FORM;

        }

        if (!form.getPassword().equals(form.getVerifyPassword())) {
            bindingResult.rejectValue("verifyPassword", "Duple.joinForm.verifyPassword");
            return JOIN_FORM;

        }

        if (!bindingResult.hasErrors()) {
            joinService.join(form.toEntity());
        }

        log.info("model = {}", model);
        log.info("form {}", form);

        return "/index";
    }

}
