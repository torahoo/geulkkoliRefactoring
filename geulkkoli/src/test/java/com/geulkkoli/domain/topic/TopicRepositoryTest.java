package com.geulkkoli.domain.topic;

import com.geulkkoli.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TopicRepositoryTest {

    @Autowired
    private TopicRepository topicRepository;

    @AfterEach
    void afterEach() {
        //
    }

    @BeforeEach
    void init() {
        //주제 더미 데이터
        for(int i = 0; i<150; i++) {
            topicRepository.save(Topic.builder().topicName("testTopic"+i).build());
        }
    }

    @Test
    void findTopicByUseDateBeforeTest() {
        //given

        //when
        List<Topic> topicByUseDateBefore = topicRepository.findTopicByUseDateBefore(LocalDate.now());

        //then
        assertThat(topicByUseDateBefore.size()).isEqualTo(30);
    }

}