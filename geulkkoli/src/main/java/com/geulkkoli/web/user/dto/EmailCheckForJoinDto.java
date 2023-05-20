package com.geulkkoli.web.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class EmailCheckForJoinDto {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Length(min = 6, max = 6)
    private String authenticationNumber;

}
