package com.geulkkoli.web.post.dto;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.posthashtag.PostHashTag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    @Setter
    private String tagListString;

    private final String nickName;

    @NotBlank
    private String tagCategory;

    @NotBlank
    private String tagStatus;

    @Builder
    public EditDTO(Long postId, String title, String postBody,
                   String nickName, String tagListString, String tagCategory, String tagStatus) {
        this.postId = postId;
        this.title = title;
        this.postBody = postBody;
        this.nickName = nickName;
        this.tagListString = tagListString;
        this.tagCategory = tagCategory;
        this.tagStatus = tagStatus;
    }

    public static EditDTO toDTO (Post post) {
        List<PostHashTag> postHashTags = new ArrayList<>(post.getPostHashTags());
        postHashTags.remove(0);
        String tagStatus = postHashTags.get((postHashTags.size()-1)).getHashTag().getHashTagName();
        postHashTags.remove(postHashTags.size()-1);
        String tagCategory = postHashTags.get((postHashTags.size()-1)).getHashTag().getHashTagName();
        postHashTags.remove(postHashTags.size()-1);
        StringBuilder tags = new StringBuilder();
        for (PostHashTag name : postHashTags){
            tags.append(" #").append(name.getHashTag().getHashTagName());
        }
        return EditDTO.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .postBody(post.getPostBody())
                .nickName(post.getNickName())
                .tagListString(tags.toString())
                .tagCategory("#"+tagCategory)
                .tagStatus("#"+tagStatus)
                .build();
    }

}
