package com.geulkkoli.web.post;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Optional;

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
        postService.savePost(post);
        return "redirect:/board/list";
    }

    @GetMapping("/update/{postId}")
    public String boardUpdateForm (Model model, @PathVariable Long postId) {
        log.info("updateParam={}, postId={}", model.getAttribute("post"), postId);
        Post findPost = postService.findById(postId);
        log.info("findPost={}", findPost.getPostBody());
        model.addAttribute("post", findPost);
        return "/board/boardUpdateForm";
    }

    @PostMapping("/update/{postId}")
    public String boardUpdate (@ModelAttribute Post updateParam, @PathVariable Long postId) {
        log.info("updateParam={}, postId={}", updateParam.getPostBody(), postId);
        postService.updatePost(postId, updateParam);
        return "redirect:/board/list";
    }

    @PostConstruct
    public void init () {
        postService.savePost(new Post("testTitle01", "test postbody 01", "test nickname01"));
        postService.savePost(new Post("testTitle02", "test postbody 02", "test nickname02"));
        postService.savePost(new Post("testTitle03", "test postbody 03", "test nickname03"));

    }
}
