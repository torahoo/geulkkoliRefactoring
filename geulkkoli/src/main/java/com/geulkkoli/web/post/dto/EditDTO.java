package com.geulkkoli.web.post.dto;

import com.geulkkoli.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
public class EditDTO {

    @NotBlank
    private Long postId;

    @Setter
    @NotBlank
    @Length(min = 1, max = 100)
    private String title;

    @Setter
    @NotBlank
    @Length(min = 10, max = 10000)
    private String postBody;

    @Builder
    public EditDTO(Long postId, String title, String postBody) {
        this.postId = postId;
        this.title = title;
        this.postBody = postBody;
    }

    public Post toEntity () {
        return Post.builder()
                .title(title)
                .postBody(postBody)
                .build();
    }
}
