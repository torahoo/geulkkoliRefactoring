package com.geulkkoli.web.post;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.web.user.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시판 리스트 html로 이동
    @GetMapping("/list")
    public String postList(Model model) {
        model.addAttribute("list", postService.findAll());
        return "/post/postList";
    }

    //게시글 addForm html 로 이동
    @GetMapping("/add")
    public String postAddForm(Model model, HttpServletRequest request) {
        model.addAttribute("post", new Post());

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/post/list";
        }
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("loginUser", loginUser);
        log.info("loginUser={}", loginUser.getNickName());

        return "/post/postAddForm";
    }

    //새 게시글 등록
    @PostMapping("/add")
    public String postAdd(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
        log.info("title={}", post.getTitle());
        postService.savePost(post);
        redirectAttributes.addAttribute("addStatus", true);
        redirectAttributes.addAttribute("postId", post.getPostId());
        return "redirect:/post/read/{postId}";
    }

    //게시글 읽기 Page로 이동
    @GetMapping("/read/{postId}")
    public String postRead(Model model, @PathVariable Long postId) {
        log.info("postId={}", postId);
        Post findPost = postService.findById(postId);
        model.addAttribute("post", findPost);
        return "/post/postPage";
    }

    //게시글 수정 html로 이동
    @GetMapping("/update/{postId}")
    public String postUpdateForm(Model model, @PathVariable Long postId) {
        log.info("updateParam={}, postId={}", model.getAttribute("post"), postId);
        Post findPost = postService.findById(postId);
        log.info("findPost={}", findPost.getPostBody());
        model.addAttribute("post", findPost);
        return "/post/postEditForm";
    }

    //게시글 수정
    @PostMapping("/update/{postId}")
    public String postUpdate(@ModelAttribute Post updateParam, @PathVariable Long postId, RedirectAttributes redirectAttributes) {
        log.info("updateParam={}, postId={}", updateParam.getPostBody(), postId);
        postService.updatePost(postId, updateParam);
        redirectAttributes.addAttribute("updateStatus", true);

        return "redirect:/post/read/{postId}";
    }

    //게시글 삭제
    @DeleteMapping("/delete/{postId}")
    public String postDelete(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/post/list";
    }

    //임시저장기능 (현재는 빈 값만 들어옴)
    @PostMapping("/test")
    public String testBlanc(@ModelAttribute Post post) {
        log.info("testBlanc={}", post.getTitle());
        return "redirect:/post/list";
    }
}
