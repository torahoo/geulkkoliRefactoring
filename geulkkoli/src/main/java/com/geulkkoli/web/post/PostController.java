package com.geulkkoli.web.post;

import com.geulkkoli.application.user.CustomAuthenticationPrinciple;
import com.geulkkoli.domain.favorites.FavoriteService;
import com.geulkkoli.domain.comment.CommentsService;
import com.geulkkoli.domain.follow.service.FollowFindService;
import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserFindService;
import com.geulkkoli.web.comment.dto.CommentBodyDTO;
import com.geulkkoli.web.follow.FollowResult;
import com.geulkkoli.web.post.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserFindService userFindService;
    private final FavoriteService favoriteService;
    private final FollowFindService followFindService;
    @Value("${comm.uploadPath}")
    private String uploadPath;

    @PostMapping("/upload-file")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile multipartFile) {

        String originalFileName = multipartFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID() + extension;
        String src = "/imageFile/" + fileName;

        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists())
            uploadDir.mkdir();

        File uploadDirFile = new File(uploadPath + fileName);

        try {
            multipartFile.transferTo(uploadDirFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return src;
    }

    /**
     * @param pageable - get 파라미터 page, size, sort 캐치
     * @return
     * @PageableDefault - get 파라미터가 없을 때 기본설정 변경(기본값: page=0, size=20)
     * page: 배열과 같이 0부터 시작한다. 즉, 0 = 1page
     * size: 한 페이지에 보여줄 게시물 수
     * sort: 정렬기준
     * direction: 정렬법
     */
    // 게시판 리스트 html로 이동
    @GetMapping("/list")
    public String postList(@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                           Model model,
                           @RequestParam(defaultValue = "") String searchType,
                           @RequestParam(defaultValue = "") String searchWords) {
        PagingDTO pagingDTO = PagingDTO.listDTOtoPagingDTO(postService.searchPostFindAll(pageable, searchType, searchWords));
        model.addAttribute("page", pagingDTO);
        searchDefault(model, searchType, searchWords);
        return "/post/postList";
    }

    //게시글 addForm html 로 이동
    @GetMapping("/add")
    public String postAddForm(Model model) {
        model.addAttribute("addDTO", new AddDTO());
        return "/post/postAddForm";
    }

    //새 게시글 등록
    @PostMapping("/add")
    public String postAdd(@Validated @ModelAttribute AddDTO post, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, HttpServletResponse response)
            throws UnsupportedEncodingException {
        if (bindingResult.hasErrors()) {
            return "/post/postAddForm";
        }
        User user = userFindService.findById(post.getAuthorId());
        long postId = postService.savePost(post, user).getPostId();
        redirectAttributes.addAttribute("postId", postId);
        response.addCookie(new Cookie(URLEncoder.encode(post.getNickName(), "UTF-8"), "done"));
        return "redirect:/post/read/{postId}";
    }

    //게시글 읽기 Page로 이동
    @GetMapping("/read/{postId}")
    public String readPost(Model model, @PathVariable Long postId, HttpServletRequest request,
                           @RequestParam(defaultValue = "") String searchType,
                           @RequestParam(defaultValue = "") String searchWords,
                           @AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {
        PageDTO postPage = PageDTO.toDTO(postService.showDetailPost(postId));
        User authorUser = userFindService.findById(postPage.getAuthorId());
        request.getSession().setAttribute("pageNumber", request.getParameter("page"));

        String checkFavorite = "never clicked";

        if (Objects.isNull(authUser)) {
            log.info("로그인을 안한 사용자 접속");
            model.addAttribute("post", postPage);
            model.addAttribute("commentList", postPage.getCommentList());
            model.addAttribute("authorUser", authorUser);
            model.addAttribute("checkFavorite", checkFavorite);
            searchDefault(model, searchType, searchWords);
            return "/post/postPage";
        }

        User loggingUser = userFindService.findById(Long.parseLong(authUser.getUserId()));
        if (favoriteService.checkFavorite(postService.findById(postId), loggingUser).isEmpty()) {
            checkFavorite = "none";
        } else {
            checkFavorite = "exist";
        }
        boolean mine = loggingUser.getUserId().equals(authorUser.getUserId());
        Boolean follow = followFindService.checkFollow(loggingUser, authorUser);
        FollowResult followResult =  new FollowResult(mine, follow);

        log.info("followResult={}", followResult.isFollow());
        log.info("mine={}", followResult.isMine());
        log.info("checkFavorite={}", checkFavorite);
        model.addAttribute("followResult", followResult);
        model.addAttribute("post", postPage);
        model.addAttribute("commentList", postPage.getCommentList());
        model.addAttribute("authorUser", authorUser);
        model.addAttribute("checkFavorite", checkFavorite);
        model.addAttribute("loginUserId", authUser.getUserId());
        model.addAttribute("comments", new CommentBodyDTO());
        searchDefault(model, searchType, searchWords);
        return "/post/postPage";
    }

    //게시글 수정 html로 이동
    @GetMapping("/update/{postId}")
    public String movePostEditForm(Model model, @PathVariable Long postId,
                                   @RequestParam(defaultValue = "") String searchType,
                                   @RequestParam(defaultValue = "") String searchWords) {
        EditDTO postPage = EditDTO.toDTO(postService.findById(postId));
        model.addAttribute("editDTO", postPage);
        searchDefault(model, searchType, searchWords);
        return "/post/postEditForm";
    }

    //게시글 수정
    @PostMapping("/update/{postId}")
    public String editPost(@Validated @ModelAttribute EditDTO updateParam, BindingResult bindingResult,
                           @PathVariable Long postId, RedirectAttributes redirectAttributes, HttpServletRequest request,
                           @RequestParam(defaultValue = "") String searchType,
                           @RequestParam(defaultValue = "") String searchWords) {
        if (bindingResult.hasErrors()) {
            return "/post/postEditForm";
        }
        postService.updatePost(postId, updateParam);
        redirectAttributes.addAttribute("updateStatus", true);
        redirectAttributes.addAttribute("page", request.getSession().getAttribute("pageNumber"));

        if (searchType != null && searchWords != null) {
            redirectAttributes.addAttribute("searchType", searchType);
            redirectAttributes.addAttribute("searchWords", searchWords);
        } else {
            redirectAttributes.addAttribute("searchType", "");
            redirectAttributes.addAttribute("searchWords", "");
        }
        return "redirect:/post/read/{postId}?page={page}&searchType={searchType}&searchWords={searchWords}";
    }

    //게시글 삭제
    @DeleteMapping("/request")
    public String deletePost(@RequestParam("postId") Long postId, @RequestParam("userNickName") String userNickName) {
        postService.deletePost(postId, userNickName);
        return "redirect:/post/list";
    }

    //임시저장기능 (현재는 빈 값만 들어옴)
    @GetMapping("/savedone")
    public void testBlanc(@AuthenticationPrincipal CustomAuthenticationPrinciple authUser,
                          HttpServletResponse response) {

        Cookie cookie = new Cookie(URLEncoder.encode(authUser.getNickName()), null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private static void searchDefault(Model model, String searchType, String searchWords) {
        if (searchType != null && searchWords != null) {
            model.addAttribute("searchType", searchType);
            model.addAttribute("searchWords", searchWords);
        } else {
            model.addAttribute("searchType", "");
            model.addAttribute("searchWords", "");
        }
    }
}
