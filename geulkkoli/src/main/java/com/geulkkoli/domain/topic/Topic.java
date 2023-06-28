package com.geulkkoli.domain.topic;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Topic {
    @Id
    @Column(name = "topic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;

    @NotBlank
    @Column(name = "topic_name")
    private String topicName;

    @NotNull
    @ColumnDefault("2000-01-01")
    @Column(name = "topic_useDate")
    private LocalDate useDate;

    @NotNull
    @ColumnDefault("2000-01-01")
    @Column(name = "topic_upComingDate")
    private LocalDate upComingDate;

    @Builder
    public Topic(String topicName) {
        this.topicName = topicName;
        this.useDate = LocalDate.of(2000, 1, 1);
        this.upComingDate = LocalDate.of(2000, 1, 1);
    }

    public void settingUpComingDate(LocalDate upComingDate) {
        this.upComingDate = upComingDate;
    }

    public void settingUseDate(LocalDate useDate) {
        this.useDate = useDate;
    }
}
