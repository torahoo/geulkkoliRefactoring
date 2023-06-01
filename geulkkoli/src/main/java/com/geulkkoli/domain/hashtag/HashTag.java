package com.geulkkoli.domain.hashtag;

import com.geulkkoli.domain.post.ConfigDate;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post_hashtag.Post_HashTag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@SequenceGenerator(
        name = "hashtag_seq_generator",
        sequenceName = "hashtag_seq",
        initialValue = 10001,
        allocationSize = 1)
@Table(name = "hashtag")
public class HashTag extends ConfigDate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "hashtag_seq_generator")
    private Long hashTagId;

    @Column(nullable = false)
    private String hashTagName;

    //Post_HashTag 클라스와 일대다로 묶는 메서드
    @OneToMany(mappedBy = "hashTag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post_HashTag> postHashTags = new LinkedHashSet<>();

    @Builder
    public HashTag (String hashTagName) {
        this.hashTagName = hashTagName;
    }

    @Override
    public boolean equals(Object obj) {
        HashTag hashTag = (HashTag) obj;
        return getHashTagId() != null && Objects.equals(getHashTagId(), hashTag.getHashTagId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
