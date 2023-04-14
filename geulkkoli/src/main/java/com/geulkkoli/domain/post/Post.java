package com.geulkkoli.domain.post;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class Post {

    @Id @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(name = "body", nullable = false)
    private String postBody;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Builder
    public Post(Long authorId, String title, String postBody, String nickName) {
        this.authorId = authorId;
        this.title = title;
        this.postBody = postBody;
        this.nickName = nickName;
    }

    //제목을 바꾼다.
    public void changeTitle(String title) {
        this.title = title;
    }

    //본문을 바꾼다.
    public void changePostBody(String updateBody) {
        this.postBody = updateBody;
    }

    //별명을 바꾼다.
    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return getPostId() != null && Objects.equals(getPostId(), post.getPostId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

