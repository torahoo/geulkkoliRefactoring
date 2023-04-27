package com.geulkkoli.web.admin;

import lombok.Getter;

@Getter
public class userLockDto {
    Long postId;
    String lockReason;
    String lockDate;

    public userLockDto(Long postId, String lockReason, String lockDate) {
        this.postId = postId;
        this.lockReason = lockReason;
        this.lockDate = lockDate;
    }
}
