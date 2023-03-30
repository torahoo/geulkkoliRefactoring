package com.geulkkoli.web.user;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.LoginService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@Slf4j

public class UserController {
    public static final String LOGIN_FORM = "user/loginForm";
    private final LoginService loginService;

    public UserController(LoginService loginService) {
        this.loginService = loginService;
    }


    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String userLogin(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
        log.info("userId {} , password {}", form.getUserId(), form.getPassword());
        if (bindingResult.hasErrors()) {
            return LOGIN_FORM;
        }

        Optional<User> loginUser = loginService.login(form.getUserId(), form.getPassword());

        if (loginUser.isEmpty()) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return LOGIN_FORM;
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
        return LOGIN_FORM;
    }
}
