package com.geulkkoli.domain.follow;

import com.geulkkoli.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long FollowId;

    //어떤 유저를 팔로우 하는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object obj) {
        Follow follow = (Follow) obj;
        return getFollowId() != null && Objects.equals(getFollowId(), follow.getFollowId());
    }

    @Override
    public int hashCode() {
        return getUser().hashCode();
    }



    public void addAuthor (User user) {
        this.user = user;
    }



}
