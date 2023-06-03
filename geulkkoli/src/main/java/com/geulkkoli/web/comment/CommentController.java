package com.geulkkoli.web.comment;

import com.geulkkoli.application.user.CustomAuthenticationPrinciple;
import com.geulkkoli.domain.comment.Comments;
import com.geulkkoli.domain.comment.CommentsService;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.comment.dto.CommentBodyDTO;
import com.geulkkoli.web.comment.dto.CommentEditDTO;
import com.geulkkoli.web.comment.dto.CommentListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.geulkkoli.domain.comment.CommentsService.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentsService commentsService;
    private final PostService postService;
    private final UserService userService;

    /**
     * 댓글 작성
     * @param postId 댓글을 작성한 게시물 구분
     * @param commentBody 댓글 본문
     * @param authUser 작성자
     * @return 새로운 댓글을 포함하는 댓글리스트 반환
     */
    @PostMapping("/{postId}")
    public ResponseEntity<List<CommentListDTO>> writePostComment(@PathVariable("postId") Long postId,
                                           @Validated @RequestBody CommentBodyDTO commentBody,
                                           @AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {


        Post post = findPost(postId);
        commentsService.writeComment(commentBody, post, findUser(authUser));

        return new ResponseEntity<>(getCommentsList(post.getComments()), HttpStatus.CREATED);
    }

    /**
     * 댓글 수정
     * @param postId 변경된 댓글의 게시물 구분
     * @param commentBody 댓글 본문
     * @param authUser 작성자
     * @return 수정한 댓글을 포함한 댓글리스트 반환
     */
    @PutMapping("/{postId}")
    public List<CommentListDTO> editPostComment(@PathVariable("postId") Long postId,
                                                @Validated @RequestBody CommentEditDTO commentBody,
                                                @AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {

        Post post = findPost(postId);
        commentsService.editComment(commentBody.getCommentId(), commentBody, findUser(authUser));

        return getCommentsList(post.getComments());
    }

    @DeleteMapping
    public HttpStatus deletePostComment(@RequestBody Comments commentBody,
                                  @AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {

        commentsService.deleteComment(commentBody.getCommentId(), findUser(authUser));
        return HttpStatus.OK;
    }

    public User findUser(CustomAuthenticationPrinciple authUser) {
        return userService.findById(Long.valueOf(authUser.getUserId()));
    }

    public Post findPost(Long postId) {
        return postService.findById(postId);
    }
}
