package com.geulkkoli.domain.comment;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.comment.dto.CommentBodyDTO;
import com.geulkkoli.web.post.dto.AddDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired
    private CommentsService commentsService;
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository;

    private User user;
    private Post post01, post02;

    @AfterEach
    void afterEach () {
        commentsRepository.deleteAll();
    }

    @BeforeEach
    void init(){
        User save = User.builder()
                .email("test@naver.com")
                .userName("test")
                .nickName("test")
                .phoneNo("00000000000")
                .password("123")
                .gender("male").build();

        user = userRepository.save(save);
    }

    @BeforeEach
    void beforeEach () {
        AddDTO addDTO01 = AddDTO.builder()
                .title("testTitle01")
                .postBody("test postbody 01")
                .nickName(user.getNickName())
                .build();
        post01 = postService.savePost(addDTO01, user);

        AddDTO addDTO02 = AddDTO.builder()
                .title("testTitle02")
                .postBody("test postbody 02")
                .nickName(user.getNickName())
                .build();
        post02 = postService.savePost(addDTO02, user);
    }

    @Test
    void commentSave() {
        //given
        CommentBodyDTO commentsBody = CommentBodyDTO.builder()
                .commentBody("test")
                .build();

        //when
        commentsService.writeComment(commentsBody, post01, user);

        //then
        Comments find = new ArrayList<>(post01.getComments()).get(0);
        assertThat(commentsBody.getCommentBody()).isEqualTo(find.getCommentBody());
    }

    @Test
    void commentEdit() {
        //given
        CommentBodyDTO commentsBody = CommentBodyDTO.builder()
                .commentBody("test")
                .build();
        commentsService.writeComment(commentsBody, post01, user);


        //when
        Comments editComment = Comments.builder()
                .commentBody("edit")
                .build();
        Comments find = new ArrayList<>(post01.getComments()).get(0);
        commentsService.editComment(find.getCommentId(), editComment, user);

        //then
        assertThat(editComment.getCommentBody()).isEqualTo(find.getCommentBody());
    }

    @Test
    void commentDelete() {
        //given
        CommentBodyDTO commentsBody = CommentBodyDTO.builder()
                .commentBody("test")
                .build();
        for (int i = 0; i < 10; ++i)
            commentsService.writeComment(commentsBody, post01, user);
        int size = post01.getComments().size();

        //when
        commentsService.deleteComment(post01.getComments().iterator().next().getCommentId(), user);

        //then
        assertThat(size - 1).isEqualTo(post01.getComments().size());
    }

    @Test
    void getUserComment() {
        //given
        for (int i = 0; i < 10; ++i) {
            CommentBodyDTO commentsBody = CommentBodyDTO.builder()
                    .commentBody("test")
                    .build();
            commentsService.writeComment(commentsBody, post01, user);
        }
        int count = post01.getComments().size();

        //when
        int size = commentsService.getUserComment(user).size();

        //then
        assertThat(size).isEqualTo(count);
    }
}
