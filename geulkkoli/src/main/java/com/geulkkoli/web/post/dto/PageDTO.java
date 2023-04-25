package com.geulkkoli.web.post.dto;

import com.geulkkoli.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@ToString
public class PageDTO {

    @NotBlank
    private Long postId;

    @NotBlank
    private Long authorId;

    @NotEmpty
    @Setter
    private String title;

    @NotEmpty
    @Setter
    private String postBody;

    @NotEmpty
    @Setter
    private String nickName;

    @Builder
    public PageDTO(Long postId, Long authorId, String title, String postBody, String nickName) {
        this.postId = postId;
        this.authorId = authorId;
        this.title = title;
        this.postBody = postBody;
        this.nickName = nickName;
    }

    public static PageDTO toDTO (Post post) {
        return PageDTO.builder()
                .postId(post.getPostId())
                .authorId(post.getUser().getUserId())
                .title(post.getTitle())
                .postBody(post.getPostBody())
                .nickName(post.getNickName())
                .build();
    }
}
