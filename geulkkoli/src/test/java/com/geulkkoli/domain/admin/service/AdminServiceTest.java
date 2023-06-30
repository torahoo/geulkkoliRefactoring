package com.geulkkoli.domain.admin.service;

import com.geulkkoli.domain.admin.AccountLock;
import com.geulkkoli.domain.admin.Report;
import com.geulkkoli.domain.admin.ReportRepository;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.topic.Topic;
import com.geulkkoli.domain.topic.TopicRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.admin.DailyTopicDto;
import com.geulkkoli.web.admin.ReportDto;
import com.geulkkoli.web.post.dto.AddDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminServiceImpl;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicRepository topicRepository;

    private User user;
    private User user2;

    @AfterEach
    void afterEach() {
        postRepository.deleteAll();
    }

    @BeforeEach
    void user(){
        Optional<User> byId = userRepository.findById(2L);
        user = byId.get();
    }

    @Test
    @DisplayName("게시글 작성자 조회")
    void findUserByPostId() {
        //given
        Long postId = 1L;

        //when
        User userByPostId = adminServiceImpl.findUserByPostId(postId);

        //then
        assertThat(user).isEqualTo(userByPostId);
    }

    @Test
    @DisplayName("신고받은 게시글 조회")
    void findReportedPosts() {
        //given
        Report report = user.writeReport(postRepository.findById(2L).get(), "욕설");
        Report report1 = user.writeReport(postRepository.findById(3L).get(), "비 협조적");
        Report report2 = user.writeReport(postRepository.findById(4L).get(), "점심을 안먹음");

        reportRepository.save(report);
        reportRepository.save(report1);
        reportRepository.save(report2);

        //when
        List<ReportDto> reportedPosts = adminServiceImpl.findAllReportedPost();

        //then
        assertThat(reportedPosts).hasSize(3);
    }

    @Test
    @DisplayName("유저 잠금")
    void lockUser() {
        //given
        Long userId = user.getUserId();

        //when
        AccountLock accountLock1 = adminServiceImpl.lockUser(userId, "욕설", 3L);

        //then
        assertThat(user.isLock()).isTrue();
        assertThat(accountLock1.getReason()).isEqualTo("욕설");
        assertThat(accountLock1.getLockExpirationDate()).has(new Condition<>(localDateTime -> localDateTime.isAfter(LocalDateTime.now()), "잠금시간"));
    }



    @Test @DisplayName("중간에 주제가 빈 날짜가 있을 때 제대로 채워주는지")
    void fillTopicTest() {
        //given
        List<Topic> topicsByUseDateBefore = topicRepository.findTopicsByUseDateBefore(LocalDate.now());

        List<Topic> topics = topicRepository.findTopicByUpComingDateBetween(LocalDate.now(), LocalDate.now().plusDays(29), Sort.by(Sort.Direction.ASC, "upComingDate"));


        //when
//        adminServiceImpl.fillTopic(topicsByUseDateBefore);
        //then
        assertThat(topics).hasSize(31);
    }

    @Test @DisplayName("주제를 Dto로 잘 바꿨는지")
    void findWeeklyTopicTest(){
        //given
        List<Topic> topicsByUseDateBefore = topicRepository.findTopicsByUseDateBefore(LocalDate.now());

        //when
        List<DailyTopicDto> weeklyTopic = adminServiceImpl.findWeeklyTopic();

        //then
        assertThat(weeklyTopic).hasSize(10);
    }
}