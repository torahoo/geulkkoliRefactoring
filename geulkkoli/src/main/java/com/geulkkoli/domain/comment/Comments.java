package com.geulkkoli.domain.comment;

import com.geulkkoli.domain.post.ConfigDate;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class Comments extends ConfigDate {

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

    public Comments (User user, Post post, String commentBody) {
        this.user = user;
        this.post = post;
        this.commentBody = commentBody;
    }

    //댓글 수정
    public void changeComments (String commentBody) {
        this.commentBody = commentBody;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "commentId=" + commentId +
                ", user=" + user +
                ", post=" + post +
                ", commentBody='" + commentBody + '\'' +
                '}';
    }
}
