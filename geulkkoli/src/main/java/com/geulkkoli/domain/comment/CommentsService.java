package com.geulkkoli.domain.comment;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.web.comment.dto.CommentListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;

    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public void writeComment(Comments commentBody, Post post, User user) {
        commentsRepository.save(user.writeComment(commentBody, post));
    }

    public static List<CommentListDTO> getCommentsList(Set<Comments> comments) {
        List<CommentListDTO> listDTO = new ArrayList<>();
        for (Comments comment : comments) {
            listDTO.add(CommentListDTO.toDTO(comment));
        }
        return listDTO;
    }

    public void editComment(Long commentId, Comments editCommentBody, User user) {
        commentsRepository.save(user.editComment(commentId, editCommentBody));
    }

    public void deleteComment(Long commentId, User user) {
        commentsRepository.delete(user.deleteComment(commentId));
    }

    public Set<Comments> getUserComment(User user) {
        return commentsRepository.findAllByUser_UserId(user.getUserId());
    }
}
