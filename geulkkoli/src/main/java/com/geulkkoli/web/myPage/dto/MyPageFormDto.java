package com.geulkkoli.web.myPage.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
public class MyPageFormDto {

    @NotEmpty
    private String userName;

    @NotEmpty
    private String email;

    public void myPageFormDto(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }



}
