package com.geulkkoli.domain.posthashtag;

import com.geulkkoli.domain.hashtag.HashTag;
import com.geulkkoli.domain.post.ConfigDate;
import com.geulkkoli.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class PostHashTag extends ConfigDate {

    @Id @Column(name = "postHashtag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postHashTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private HashTag hashTag;

    @Builder
    public PostHashTag(Post post, HashTag hashTag) {
        this.post = post;
        this.hashTag = hashTag;
    }

    @Override
    public boolean equals (Object obj) {
        PostHashTag postHashTag = (PostHashTag) obj;
        return getPostHashTagId() != null && Objects.equals(getPostHashTagId(), postHashTag.getPostHashTagId());
    }
}
