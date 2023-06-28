package com.geulkkoli.web.home;

import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.posthashtag.PostHashTagService;
import com.geulkkoli.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
public class HomeController {

    private final PostHashTagService postHashTagService;

    @GetMapping
    public String home(@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model,
                       @RequestParam(defaultValue = "") String searchType,
                       @RequestParam(defaultValue = "") String searchWords) {

        model.addAttribute("list", postHashTagService.searchPostsListByHashTag(pageable, searchType, searchWords).toList());
        model.addAttribute("notificationList", postHashTagService.searchPostsListByHashTag(pageable, searchType, searchWords+"#공지글").toList());
        model.addAttribute("todayTopic", postHashTagService.showTodayTopic(LocalDate.now()));

        return "/home";
    }
}

