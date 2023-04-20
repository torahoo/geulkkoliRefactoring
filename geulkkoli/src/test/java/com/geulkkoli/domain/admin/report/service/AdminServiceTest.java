package com.geulkkoli.domain.admin.report.service;

import com.geulkkoli.domain.admin.report.Report;
import com.geulkkoli.domain.admin.report.ReportRepository;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class AdminServiceTest {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("신고를 저장한다.")
    @Transactional
    void save() {
        Post reportedPost = Post.builder()
                .authorId(1L)
                .nickName("바나나")
                .postBody("나는 멋지고 섹시한 개발자")//채&훈
                .title("여러분")
                .build();
        postRepository.save(reportedPost);

        User reporter = User.builder()
                .email("tako@naver.com")
                .userName("김")
                .nickName("바나나")
                .password("12345")
                .phoneNo("01012345678")
                .gender("male")
                .build();
        userRepository.save(reporter);

        Report report = Report.of(reportedPost, reporter, LocalDateTime.now());
        Report save = reportRepository.save(report);

        assertThat(report).isEqualTo(save);
    }
}