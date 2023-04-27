package com.geulkkoli.web.home;

import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/home")
public class HomeController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("list", postService.findAll());
        return "/home";
    }
}

