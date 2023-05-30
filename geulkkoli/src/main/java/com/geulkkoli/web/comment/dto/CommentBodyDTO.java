package com.geulkkoli.web.comment.dto;

import com.geulkkoli.domain.comment.Comments;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@RequiredArgsConstructor
public class CommentBodyDTO {

    @NotBlank
    @Length(min = 2, max = 200)
    String commentBody;

    public Comments transComments() {
        return new Comments(this.commentBody);
    }
}
