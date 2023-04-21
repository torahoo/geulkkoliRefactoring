package com.geulkkoli.web.post;

import com.geulkkoli.domain.post.entity.Post;
import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.post.dto.AddDTO;
import com.geulkkoli.web.post.dto.EditDTO;
import com.geulkkoli.web.post.dto.PageDTO;
import com.geulkkoli.web.user.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    // 게시판 리스트 html로 이동
    @GetMapping("/list")
    public String postList(Model model) {
        model.addAttribute("list", postService.findAll());
        return "/post/postList";
    }

    //게시글 addForm html 로 이동
    @GetMapping("/add")
    public String postAddForm(Model model, HttpServletRequest request) {
        model.addAttribute("post", new AddDTO());// 빈 객체?

        HttpSession session = request.getSession(false);
        if (Objects.isNull(session)|| Objects.isNull(session.getAttribute(SessionConst.LOGIN_USER))) {
            return "redirect:/post/list";
        }
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);

        model.addAttribute("loginUser", loginUser);
        log.info("loginUser={}", loginUser.getNickName());

        return "/post/postAddForm";
    }

    //새 게시글 등록
    @PostMapping("/add")
    public String postAdd(@ModelAttribute AddDTO post, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        log.info("title={}", post.getTitle());

        HttpSession session = request.getSession(false);
        if (Objects.isNull(session)|| Objects.isNull(session.getAttribute(SessionConst.LOGIN_USER))) {
            return "redirect:/post/list";
        }
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        post.setNickName(loginUser.getNickName());
        post.setAuthorId(loginUser.getUserId());

        Long postId = postService.savePost(post);
        redirectAttributes.addAttribute("addStatus", true);
        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/post/read/{postId}";
    }

    //게시글 읽기 Page로 이동
    @GetMapping("/read/{postId}")
    public String postRead(Model model, @PathVariable Long postId) {
        log.info("postId={}", postId);
        PageDTO postPage = PageDTO.toDTO(postService.findById(postId));
        User authorUser = userService.findById(postPage.getAuthorId());
        model.addAttribute("post", postPage);
        model.addAttribute("authorUser", authorUser);
        return "/post/postPage";
    }

    //게시글 수정 html로 이동
    @GetMapping("/update/{postId}")
    public String postUpdateForm(Model model, @PathVariable Long postId) {
        log.info("updateParam={}, postId={}", model.getAttribute("post"), postId);
        PageDTO postPage = PageDTO.toDTO(postService.findById(postId));
        log.info("findPost={}", postPage.getPostBody());
        model.addAttribute("post", postPage);
        return "/post/postEditForm";
    }

    //게시글 수정
    @PostMapping("/update/{postId}")
    public String postUpdate(@ModelAttribute EditDTO updateParam, @PathVariable Long postId, RedirectAttributes redirectAttributes) {
        log.info("updateParam={}, postId={}", updateParam.getPostBody(), postId);
        postService.updatePost(postId, updateParam.toEntity());
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
