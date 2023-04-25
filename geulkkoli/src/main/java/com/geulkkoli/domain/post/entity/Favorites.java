package com.geulkkoli.domain.post.entity;

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
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoritesId;

    //어던 게시글의 좋아요 인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //누가 좋아요를 눌렀는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object obj) {
        Favorites favorites = (Favorites) obj;
        return getFavoritesId() != null && Objects.equals(getFavoritesId(), favorites.getFavoritesId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    //==연관관계 메서드==//

    /**
     * 유저 세팅
     */
    public void addAuthor (User user) {
        this.user = user;
        user.getFavorites().add(this);
    }

    /**
     * 게시글 세팅
     */
    public void addPost (Post post) {
        this.post = post;
        post.getFavorites().add(this);
    }
}
