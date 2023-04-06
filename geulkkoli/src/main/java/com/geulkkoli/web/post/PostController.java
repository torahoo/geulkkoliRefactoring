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
        redirectAttributes.addAttribute("postId", post.getPostId());
        return "redirect:/post/read/{postId}";
    }

    @GetMapping("/read/{postId}")
    public String postRead(Model model, @PathVariable Long postId) {
        log.info("postId={}", postId);
        Post findPost = postService.findById(postId);
        model.addAttribute("post", findPost);
        return "/post/postPage";
    }

    @GetMapping("/update/{postId}")
    public String postUpdateForm(Model model, @PathVariable Long postId) {
        log.info("updateParam={}, postId={}", model.getAttribute("post"), postId);
        Post findPost = postService.findById(postId);
        log.info("findPost={}", findPost.getPostBody());
        model.addAttribute("post", findPost);
        return "/post/postEditForm";
    }

    @PostMapping("/update/{postId}")
    public String postUpdate(@ModelAttribute Post updateParam, @PathVariable Long postId, RedirectAttributes redirectAttributes) {
        log.info("updateParam={}, postId={}", updateParam.getPostBody(), postId);
        postService.updatePost(postId, updateParam);
        redirectAttributes.addAttribute("updateStatus", true);

        return "redirect:/post/read/{postId}";
    }

    @DeleteMapping("/delete/{postId}")
    public String postDelete(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/post/list";
    }

    @PostMapping("/test")
    public String testBlanc(@ModelAttribute Post post) {
        log.info("testBlanc={}", post.getTitle());
        return "redirect:/post/list";
    }
}
