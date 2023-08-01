package com.geulkkoli.application.user;

import lombok.*;

@Setter
@Getter
public class EmailDto {

    // 수신할 회원 이메일, 제목, 내용
    private String to;
    private String subject;
    private String text;

    public EmailDto (){};

}
