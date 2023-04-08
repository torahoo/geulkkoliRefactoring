package com.geulkkoli.web.user.edit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class EditUpdateForm {


    @NotEmpty
    private String userName;

    @NotEmpty
    @Length(min = 8, max = 20)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[*\\W])(?=\\S+$).{8,20}")
    private String password;

    @NotEmpty
    private String verifyPassword;

    @NotEmpty
    @Length(min = 2, max = 8)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$")
         private String nickName;

    @NotEmpty
    @Length(min = 10, max = 11)
    @Pattern(regexp = "^[*\\d]*$")
    private String phoneNo;

    @NotEmpty
    private String gender;

//    public User toEntity() {
//        return User.builder()
//                .userName(userName)
//                .password(password)
//                .nickName(nickName)
//                .phoneNo(phoneNo)
//                .gender(gender)
//                .build();
//
//    }

}
