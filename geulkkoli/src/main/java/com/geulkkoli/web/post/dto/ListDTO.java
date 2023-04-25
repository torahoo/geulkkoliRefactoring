package com.geulkkoli.web.post.dto;

import com.geulkkoli.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class ListDTO {

    @NotBlank
    private Long postId;

    @NotBlank
    private String title;

    @NotBlank
    private String nickName;

    @Builder
    public ListDTO(Long postId, String title, String nickName) {
        this.postId = postId;
        this.title = title;
        this.nickName = nickName;
    }

    public static ListDTO toDTO (Post post) {
        return ListDTO.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .nickName(post.getNickName())
                .build();
    }
}
