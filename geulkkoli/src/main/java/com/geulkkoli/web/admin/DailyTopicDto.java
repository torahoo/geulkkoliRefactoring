package com.geulkkoli.web.admin;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.*;
@Getter
@Setter
public class DailyTopicDto {

    private String date;
    private String theme;

    public DailyTopicDto(){}

    @Builder
    public DailyTopicDto(String date, String theme) {
        this.date = date;
        this.theme = theme;
    }

    public static String dateToString (LocalDate date) {
        return date.toString();
    }

    public static LocalDate stringToDate (String date) {
        String [] split = date.split("-");
        LocalDate localDate = LocalDate.of(
                Integer.parseInt(split[0].strip()),
                Integer.parseInt(split[1].strip()),
                Integer.parseInt(split[2].strip())
        );
        return localDate;
    }
}
