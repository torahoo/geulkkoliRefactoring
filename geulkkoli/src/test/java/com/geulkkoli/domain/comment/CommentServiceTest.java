package com.geulkkoli.domain.comment;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.web.comment.dto.CommentBodyDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentsServiceTest {
    @Mock
    private CommentsRepository commentsRepository;

    @InjectMocks
    private CommentsService commentsService;


    @Test
    void writeComment() {
        User user = User.builder()
                .email("test1@gmail.com")
                .userName("name")
                .nickName("nickName")
                .phoneNo("010-1111-2222")
                .password("1234")
                .gender("Male")
                .build();
        ReflectionTestUtils.setField(user, "userId", 1L);
        Post post = Post.builder()
                .nickName(user.getNickName())
                .title("title")
                .user(user)
                .postBody("test")
                .build();
        ReflectionTestUtils.setField(post, "postId", 1L);

        CommentBodyDTO commentBodyDTO = CommentBodyDTO.builder()
                .commentBody("test")
                .build();
        Comments comments = new Comments(user, post, commentBodyDTO.getCommentBody());
        ReflectionTestUtils.setField(comments, "commentId", 1L);
        given(commentsRepository.save(any(Comments.class))).willReturn(comments);
        Comments save = commentsService.writeComment(commentBodyDTO, post, user);
        assertThat(save).isEqualTo(comments);
    }

    @Test
    void getCommentsList() {
    }

    @Test
    void editComment() {
    }

    @Test
    void deleteComment() {
    }

    @Test
    void getUserComment() {
    }
}
