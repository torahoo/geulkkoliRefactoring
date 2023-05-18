package com.geulkkoli.domain.user;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ConfigDate {

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private String signUpDate;

    @Transient
    private LocalDateTime calendarData;

    @PrePersist
    public void onPrePersist() {
        if (calendarData != null) {
            this.signUpDate = calendarData.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        } else {
            this.signUpDate= LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        }
    }

    // 달력 잔디 심기용 달력 시작 기준 날짜 필요
    public LocalDateTime setCreatedAtForCalendarTest(LocalDateTime localDateTime) {
        return calendarData = localDateTime;
    }
}
