package com.geulkkoli.web.post.dto;

import com.geulkkoli.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class EditDTO {

    @NotNull
    private Long postId;

    @Setter
    @NotBlank
    @Length(min = 1, max = 100)
    private String title;

    @Setter
    @NotBlank
    @Length(min = 10, max = 10000)
    private String postBody;

    private final String nickName;

    @Builder
    public EditDTO(Long postId, String title, String postBody, String nickName) {
        this.postId = postId;
        this.title = title;
        this.postBody = postBody;
        this.nickName = nickName;
    }

    public static EditDTO toDTO (Post post) {
        return EditDTO.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .postBody(post.getPostBody())
                .nickName(post.getNickName())
                .build();
    }

    public Post toEntity () {
        return Post.builder()
                .title(title)
                .postBody(postBody)
                .build();
    }
}
