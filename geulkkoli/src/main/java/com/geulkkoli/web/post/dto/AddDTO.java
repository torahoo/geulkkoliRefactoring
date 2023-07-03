package com.geulkkoli.web.post.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    private String tagListString ="";

    private String tagCategory;

    private String tagStatus;

    @Builder
    public AddDTO(Long authorId, String title, String postBody
            , String nickName, String tagListString, String tagCategory, String tagStatus) {
        this.authorId = authorId;
        this.title = title;
        this.postBody = postBody;
        this.nickName = nickName;
        this.tagListString = tagListString;
        this.tagCategory = tagCategory;
        this.tagStatus = tagStatus;
    }

}
