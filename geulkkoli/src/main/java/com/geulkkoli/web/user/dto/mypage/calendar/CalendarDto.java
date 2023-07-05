package com.geulkkoli.web.user.dto.mypage.calendar;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

@Getter
public class CalendarDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String signUpDate;

    private List<String> allPostDatesByOneUser;

    public CalendarDto(String email, String signUpDate, List<String> allPostDatesByOneUser) {
        this.email = email;
        this.signUpDate = signUpDate;
        this.allPostDatesByOneUser =allPostDatesByOneUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarDto)) return false;
        CalendarDto that = (CalendarDto) o;
        return Objects.equals(email, that.email) && Objects.equals(signUpDate, that.signUpDate) && Objects.equals(allPostDatesByOneUser, that.allPostDatesByOneUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, signUpDate, allPostDatesByOneUser);
    }

    @Override
    public String toString() {
        return "CalendarDto{" +
                "email='" + email + '\'' +
                ", signUpDate='" + signUpDate + '\'' +
                ", allPostDatesByOneUser=" + allPostDatesByOneUser +
                '}';
    }
}
