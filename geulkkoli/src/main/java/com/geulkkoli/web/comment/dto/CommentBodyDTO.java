package com.geulkkoli.web.comment.dto;

import com.geulkkoli.domain.comment.Comments;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@RequiredArgsConstructor
public class CommentBodyDTO {

    @NotBlank
    @Size(min = 2, max = 200, message = "댓글은 2자 이상 200자 이하로 입력해주세요.")
    String commentBody;

    @Builder
    public CommentBodyDTO(String commentBody) {
        this.commentBody = commentBody;
    }
    public Comments transComments() {
        return new Comments(this.commentBody);
    }
    public String getCommentBody() {
        return commentBody;
    }


}
