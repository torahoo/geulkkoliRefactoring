package com.geulkkoli.application.follow;

import java.time.LocalDateTime;

public class FollowInfo {
    private final Long id;

    private final Long userId;
    private final String nickName;
    private final LocalDateTime createdAt;

    public FollowInfo(Long id, Long userId, String nickName, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.nickName = nickName;
        this.createdAt = createdAt;
    }

    public String getNickName() {
        return nickName;
    }

    public String getCreatedAt() {
        return createdAt.toString();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }
}
