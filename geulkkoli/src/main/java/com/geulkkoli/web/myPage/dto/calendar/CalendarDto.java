package com.geulkkoli.web.myPage.dto.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private LocalDate signUpDate;

}
