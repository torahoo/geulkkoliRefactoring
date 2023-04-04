package com.geulkkoli.web.post;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String boardAdd(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
        log.info("title={}", post.getTitle());
        postService.savePost(post);
        redirectAttributes.addAttribute("addStatus", true);
        redirectAttributes.addAttribute("postNo", post.getPostNo());
        return "redirect:/board/read/{postNo}";
    }

    @GetMapping("/read/{postNo}")
    public String boardRead(Model model, @PathVariable Long postNo) {
        log.info("postNo={}", postNo);
        Post findPost = postService.findById(postNo);
        model.addAttribute("post", findPost);
        return "/board/boardRead";
    }

    @GetMapping("/update/{postNo}")
    public String boardUpdateForm(Model model, @PathVariable Long postNo) {
        log.info("updateParam={}, postNo={}", model.getAttribute("post"), postNo);
        Post findPost = postService.findById(postNo);
        log.info("findPost={}", findPost.getPostBody());
        model.addAttribute("post", findPost);
        return "/board/boardUpdateForm";
    }

    @PutMapping("/update/{postNo}")
    public String boardUpdate(@ModelAttribute Post updateParam, @PathVariable Long postNo, RedirectAttributes redirectAttributes) {
        log.info("updateParam={}, postId={}", updateParam.getPostBody(), postNo);
        postService.updatePost(postNo, updateParam);
        redirectAttributes.addAttribute("updateStatus", true);

        return "redirect:/board/read/{postNo}";
    }

    @DeleteMapping("/delete/{postNo}")
    public String boardDelete(@PathVariable Long postNo) {
        log.info("deletePostNo={}", postNo);
        postService.deletePost(postNo);

        return "redirect:/board/list";
    }

    @PostConstruct
    public void init() {
        postService.savePost(new Post("testTitle01", "test postbody 01", "test nickname01"));
        postService.savePost(new Post("testTitle02", "test postbody 02", "test nickname02"));
        postService.savePost(new Post("testTitle03", "test postbody 03", "test nickname03"));

    }

    @PostMapping("/test")
    public String testBlanc(@ModelAttribute Post post) {
        log.info("testBlanc={}", post.getTitle());
//        postService.savePost(post);
        return "redirect:/board/list";
    }
}
