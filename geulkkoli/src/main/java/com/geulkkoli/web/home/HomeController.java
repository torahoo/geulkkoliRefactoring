package com.geulkkoli.web.home;

import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.user.dto.LoginFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
public class HomeController {

    public static final String LOGIN_FORM = "user/loginForm";

    private final PostService postService;

    @GetMapping
    public String home(@PageableDefault(size = 5, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model,
                       @RequestParam(defaultValue = "7") String searchType,
                       @RequestParam(defaultValue = "7") String searchWords) {

//        model.addAttribute("list", postService.searchPostFindAll(pageable, searchType, searchWords).toList());

        return "/home";
    }

    @GetMapping("/loginPage")
    public String loginForm(@ModelAttribute("loginForm") LoginFormDto form) {

        return LOGIN_FORM;
    }
}

