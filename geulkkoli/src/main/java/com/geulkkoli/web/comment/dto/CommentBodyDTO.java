package com.geulkkoli.web.comment.dto;

import com.geulkkoli.domain.comment.Comments;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Setter
@RequiredArgsConstructor
public class CommentBodyDTO {

    @NotNull
    @Length(min = 2, max = 200)
    String commentBody;

    public Comments transComments() {
        return new Comments(this.commentBody);
    }
}
