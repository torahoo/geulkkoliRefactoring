package com.geulkkoli.web.admin;

import lombok.Getter;

@Getter
public class UserLockDto {
    Long postId;
    String lockReason;
    String lockDate;

    public UserLockDto(Long postId, String lockReason, String lockDate) {
        this.postId = postId;
        this.lockReason = lockReason;
        this.lockDate = lockDate;
    }
}
