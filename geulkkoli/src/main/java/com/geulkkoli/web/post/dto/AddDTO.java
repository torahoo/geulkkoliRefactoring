package com.geulkkoli.web.post.dto;

import com.geulkkoli.domain.post.entity.Post;
import com.geulkkoli.domain.user.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AddDTO {

    @NotNull
    private Long authorId;

    @NotBlank
    @Length(min = 1, max = 100)
    private String title;

    @NotBlank
    @Length(min = 10, max = 10000)
    private String postBody;

    @NotBlank
    private String nickName;

    @Builder
    public AddDTO(Long authorId, String title, String postBody, String nickName) {
        this.authorId = authorId;
        this.title = title;
        this.postBody = postBody;
        this.nickName = nickName;
    }

    public Post toEntity (User user) {
        return Post.builder()
                .title(title)
                .postBody(postBody)
                .nickName(nickName)
                .user(user)
                .build();
    }
}
