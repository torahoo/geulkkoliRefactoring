package com.geulkkoli.domain.post.entity;

import com.geulkkoli.domain.post.ConfigDate;
import com.geulkkoli.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class Comments extends ConfigDate{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    //댓글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //댓글이 어떤 게시글에 있는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String commentBody;

    @Override
    public boolean equals(Object obj) {
        Comments comments = (Comments) obj;
        return getCommentId() != null && Objects.equals(getCommentId(), comments.getCommentId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Builder
    public Comments (String commentBody) {
        this.commentBody = commentBody;
    }

    //==연관관계 메서드==//

    /**
     * 댓글의 작성자
     */
    public void addAuthor (User user) {
        this.user = user;
    }

    /**
     * 댓글을 단 게시글
     */
    public void addPost (Post post) {
        this.post = post;
    }

    public void changeComments (String commentBody) {
        this.commentBody = commentBody;
    }
}
