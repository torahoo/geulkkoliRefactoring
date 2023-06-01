package com.geulkkoli.domain.post_hashtag;

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
public class Post_HashTag extends ConfigDate {

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
    public Post_HashTag(Post post, HashTag hashTag) {
        this.post = post;
        this.hashTag = hashTag;
    }

    @Override
    public boolean equals (Object obj) {
        Post_HashTag postHashTag = (Post_HashTag) obj;
        return getPostHashTagId() != null && Objects.equals(getPostHashTagId(), postHashTag.getPostHashTagId());
    }
}
