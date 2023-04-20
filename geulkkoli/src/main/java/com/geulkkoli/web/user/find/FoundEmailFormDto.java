package com.geulkkoli.web.user.find;

import lombok.Getter;

@Getter
public class FoundEmailFormDto {

    private final String email;

    public FoundEmailFormDto(String email) {
        this.email = email;
    }

}
