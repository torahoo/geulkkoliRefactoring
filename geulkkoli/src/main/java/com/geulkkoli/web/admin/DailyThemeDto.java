package com.geulkkoli.web.admin;


import lombok.Getter;
import lombok.Setter;

import java.time.*;
@Getter
@Setter
public class DailyThemeDto {
    private String date;
    private String theme;

    public DailyThemeDto(String date, String theme) {
        this.date = date;
        this.theme = theme;
    }

    public DailyThemeDto() {
        this.date = LocalDate.now().toString();
        this.theme = "default";
    }
}
