package com.geulkkoli.web.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class JoinForm {

    @NotEmpty
    private String userId;
    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;
    @NotEmpty
    private String nickName;

    private String email;
    private Integer phoneNo;
    private String sex;

}
