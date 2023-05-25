package com.geulkkoli.web.comment.dto;

import lombok.Builder;

import java.time.LocalDateTime;


public class CommentDto {

    private String commentBody;

    private String nickName;

    private LocalDateTime date;

    public CommentDto() {
    }

    @Builder
    public CommentDto(String commentBody, String nickName, LocalDateTime date) {
        this.commentBody = commentBody;
        this.nickName = nickName;
        this.date = date;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public String getNickName() {
        return nickName;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
