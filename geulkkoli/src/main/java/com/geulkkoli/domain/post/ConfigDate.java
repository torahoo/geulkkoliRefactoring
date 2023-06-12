package com.geulkkoli.domain.post;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
    private String createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private String updatedAt;

    @Transient
    private LocalDateTime calendarData;

    @PrePersist
    public void onPrePersist() {
        if (calendarData != null) {
            this.createdAt = calendarData.format(DateTimeFormatter.ofPattern("yyyy. MM. dd a hh:mm:ss"));
        } else {
            this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy. MM. dd a hh:mm:ss"));
        }
        this.updatedAt = createdAt;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    // 달력 잔디 심기용 각 다른 날짜의 게시물들 필요 (추후 제거)
    public LocalDateTime setCreatedAtForCalendarTest(LocalDateTime localDateTime) {
        return calendarData = localDateTime;
    }
}
