package com.geulkkoli.domain.topic;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Topic {
    @Id @Column(name = "topic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;

    @NotBlank @Column(name = "topic_name")
    private String topicName;
    @NotBlank
    private LocalDate useDate;
    @NotBlank
    private LocalDate upComingDate;

    @Builder
    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public void settingUpComingDate (LocalDate upComingDate){
        this.upComingDate = upComingDate;
    }
    public void settingUseDate (LocalDate useDate){
        this.useDate = useDate;
    }
}
