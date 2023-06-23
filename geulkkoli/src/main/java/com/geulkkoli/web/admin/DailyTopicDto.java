package com.geulkkoli.web.admin;


import lombok.Getter;
import lombok.Setter;

import java.time.*;
@Getter
@Setter
public class DailyTopicDto {
    private String date;
    private String theme;

    public DailyTopicDto(String date, String theme) {
        this.date = date;
        this.theme = theme;
    }

    public DailyTopicDto() {
        this.date = LocalDate.now().toString();
        this.theme = "default";
    }
}
