package com.geulkkoli.web.user.find;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class ResetPasswordFormDto {

    @NotEmpty
    @Length(min = 8, max = 20)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[*\\W])(?=\\S+$).{8,20}")
    private String password;

    @NotEmpty
    private String verifyPassword;

    public ResetPasswordFormDto(String password, String verifyPassword) {
        this.password = password;
        this.verifyPassword = verifyPassword;
    }
}
