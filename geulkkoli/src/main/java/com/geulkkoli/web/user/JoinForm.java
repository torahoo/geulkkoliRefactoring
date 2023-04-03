package com.geulkkoli.web.user;

import com.geulkkoli.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
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

    @Email
    private String email;

    private Integer phoneNo;
    private String sex;

    public User toEntity(){
        return User.builder()
                .userId(userId)
                .userName(userName)
                .password(password)
                .nickName(nickName)
                .email(email)
                .phoneNo(phoneNo)
                .sex(sex)
                .build();

    }

}
