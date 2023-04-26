package com.geulkkoli.domain.post;

import com.geulkkoli.domain.comment.Comments;
import com.geulkkoli.domain.favorites.Favorites;
import com.geulkkoli.domain.hashtag.HashTags;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.web.post.dto.AddDTO;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class Post extends ConfigDate {

    @Id @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    //게시글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(name = "body", nullable = false)
    private String postBody;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Setter
    @Column(nullable = false)
    private int postHits;

    @Setter
    private String imageUploadName;

    //댓글의 게시글 매핑
    @OneToMany(mappedBy = "post")
    private Set<Comments> comments = new LinkedHashSet<>();

    //좋아요의 게시글 매핑
    @OneToMany(mappedBy = "post")
    private Set<Favorites> favorites = new LinkedHashSet<>();

    //해시태그의 게시글 매핑
    @OneToMany(mappedBy = "post")
    private Set<HashTags> hashTags = new LinkedHashSet<>();

    @Builder
    public Post(String title, String postBody, String nickName) {
        this.title = title;
        this.postBody = postBody;
        this.nickName = nickName;
    }

    public Post(AddDTO addDTO, User user) {
        this.title = addDTO.getTitle();
        this.postBody = addDTO.getPostBody();
        this.nickName = addDTO.getNickName();
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        Post post = (Post) o;
        return getPostId() != null && Objects.equals(getPostId(), post.getPostId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    //==연관관계 메서드==//

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

    //유저를 등록한다.
    public void addAuthor (User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    //조회수를 바꾼다.
    public void changeHits (int postHits) {
        this.postHits = postHits;
    }

}

