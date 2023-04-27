package com.geulkkoli.web.admin;

import lombok.Getter;

@Getter
public class userLockDto {
    Long userId;
    String lockReason;
    String lockDate;

    public userLockDto(Long userId, String lockReason, String lockDate) {
        this.userId = userId;
        this.lockReason = lockReason;
        this.lockDate = lockDate;
    }
}
