package com.geulkkoli.web.home;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
public class HomeController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping
    public String home(@PageableDefault(size = 5, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model, HttpServletRequest request) {

        String searchType = request.getParameter("searchType");
        String searchWords = request.getParameter("searchWords");
        model.addAttribute("list", postService.findAll(pageable, searchType, searchWords).toList());

        return "/home";
    }
}

