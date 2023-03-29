package com.geulkkoli.web.post;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public String boardList(Model model) {
        model.addAttribute("list", postService.findAll());
        return "/board/boardList";
    }

    @GetMapping("/add")
    public String boardAddForm(Model model) {
        model.addAttribute("post", new Post());
        return "/board/boardAddForm";
    }

    @PostMapping("/add")
    public String boardAdd(@ModelAttribute Post post) {
        log.info("title={}", post.getTitle());

        return "redirect:/board/list";
    }
}
