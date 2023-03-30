package com.geulkkoli.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class UserController {

    private LoginForm form;

    @GetMapping("/login")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String userLogin(@ModelAttribute LoginForm form) {
        log.info("userId {} , password {}", form.getUserId(), form.getPassword());
        return "user/loginForm";
    }
}
