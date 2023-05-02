package com.geulkkoli.domain.follow;

import com.geulkkoli.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Table(uniqueConstraints = {@UniqueConstraint(name = "FOLLOWER_FOLLOWED_USER", columnNames = {"follower_id", "followee_id"})})
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followingsId;

    //어떤 유저가 팔로우 하는지(follower)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private User followerId;

    //어떤 유저를 팔로우 하는지(followee)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id", nullable = false)
    private User followeeId;


    protected Follow() {
    }

    private Follow(User followerId, User followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    public static Follow of(User followerId, User followeeId) {
        return new Follow(followerId, followeeId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Follow)) return false;
        Follow follow = (Follow) o;
        return Objects.equals(followingsId, follow.followingsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followingsId);
    }




}
