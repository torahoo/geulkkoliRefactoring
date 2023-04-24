package com.geulkkoli.web.user;

import lombok.Getter;
import lombok.Setter;

import java.time.*;
@Getter
@Setter
public class CalendarDto {

    private String date;


    public CalendarDto(String date) {
        this.date = date;
    }

    public CalendarDto() {
        this.date = LocalDate.now().toString();
    }
}