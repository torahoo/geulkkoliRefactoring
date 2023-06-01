package com.geulkkoli.web.myPage.dto.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String signUpDate;

    private List<LocalDate> allPostDatesByOneUser;

}
