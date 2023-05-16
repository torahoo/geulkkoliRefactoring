package com.geulkkoli.web.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geulkkoli.application.user.AuthUser;
import com.geulkkoli.domain.comment.Comments;
import com.geulkkoli.domain.comment.CommentsService;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.comment.dto.CommentListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentsService commentsService;
    private final PostService postService;
    private final UserService userService;

    @PostMapping("/writePostComment/{postId}")
    public List<CommentListDTO> writePostComment(@PathVariable("postId") String postId,
                                                 @RequestBody Comments commentBody,
                                                 @AuthenticationPrincipal AuthUser user) throws Exception {

        log.info("postId={}", postId);
        log.info("commentBody={}", commentBody.getCommentBody());
        log.info("authUser={}", user.getNickName());


        Post post = postService.findById(Long.valueOf(postId));

        commentsService.writeComment(
                commentBody, post, userService.findById(user.getUserId()));

        return CommentsService.getCommentsList(post.getComments());
    }

    @PutMapping("/editPostComment")
    public void editPostComment() {

    }

    @DeleteMapping("/deletePostComment")
    public void deletePostComment() {

    }
}
