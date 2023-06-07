package com.geulkkoli.domain.follow;

import com.geulkkoli.domain.user.User;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "user_followings", uniqueConstraints = @UniqueConstraint(columnNames = {"followee_id", "follower_id"}))
@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // follower가 followee를 팔로우한다.
    @JoinColumn(name = "followee_id")
    @ManyToOne
    private User followee;
    @JoinColumn(name = "follower_id")
    @ManyToOne
    private User follower;

    public Follow() {
    }


    private Follow(User followee, User follower) {
        this.followee = followee;
        this.follower = follower;
    }

    public static Follow of(User followee, User follower){
        return new Follow(followee, follower);
    }

    public boolean isFollowee(User followee) {
        return this.followee.equals(followee);
    }
    public Long getId() {
        return id;
    }

    public User getFollowee() {
        return followee;
    }

    public String followeeNickName() {
        return followee.getNickName();
    }

    public User getFollower() {
        return follower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Follow)) return false;
        Follow that = (Follow) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
