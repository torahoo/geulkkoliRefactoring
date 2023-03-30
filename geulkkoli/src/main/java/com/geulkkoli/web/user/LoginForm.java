package com.geulkkoli.web.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class LoginForm {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String password;
}
