package com.geulkkoli.web.post;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public String postList(Model model) {
        model.addAttribute("list", postService.findAll());
        return "/post/postList";
    }

    @GetMapping("/add")
    public String postAddForm(Model model) {
        model.addAttribute("post", new Post());
        return "/post/postAddForm";
    }

    @PostMapping("/add")
    public String postAdd(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
        log.info("title={}", post.getTitle());
        postService.savePost(post);
        redirectAttributes.addAttribute("addStatus", true);
        redirectAttributes.addAttribute("postNo", post.getPostNo());
        return "redirect:/post/read/{postNo}";
    }

    @GetMapping("/read/{postNo}")
    public String postRead(Model model, @PathVariable Long postNo) {
        log.info("postNo={}", postNo);
        Post findPost = postService.findById(postNo);
        model.addAttribute("post", findPost);
        return "/post/postPage";
    }

    @GetMapping("/update/{postNo}")
    public String postUpdateForm(Model model, @PathVariable Long postNo) {
        log.info("updateParam={}, postNo={}", model.getAttribute("post"), postNo);
        Post findPost = postService.findById(postNo);
        log.info("findPost={}", findPost.getPostBody());
        model.addAttribute("post", findPost);
        return "/post/postEditForm";
    }

    @PostMapping("/update/{postNo}")
    public String postUpdate(@ModelAttribute Post updateParam, @PathVariable Long postNo, RedirectAttributes redirectAttributes) {
        log.info("updateParam={}, postId={}", updateParam.getPostBody(), postNo);
        postService.updatePost(postNo, updateParam);
        redirectAttributes.addAttribute("updateStatus", true);

        return "redirect:/post/read/{postNo}";
    }

    @DeleteMapping("/delete/{postNo}")
    public String postDelete(@PathVariable Long postNo) {
        postService.deletePost(postNo);
        return "redirect:/post/list";
    }

    @PostMapping("/test")
    public String testBlanc(@ModelAttribute Post post) {
        log.info("testBlanc={}", post.getTitle());
        return "redirect:/post/list";
    }
}
