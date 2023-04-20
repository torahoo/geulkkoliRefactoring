package com.geulkkoli.domain.admin.report;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.user.User;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * TODO
 *  신고된 게시글 모아보기
 *  불온한 회원 정지시키기
 *  관리자는 회원의 규정을 어긴 이력을 볼 수 있다.
 *  관리자는 회원의 계정을 잠금 시킬 수 있다.
 *  관리자는 회원의 계정을 영구 정지 시킬 수 있다.
 */
@Getter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "REPORTER_REPORTRED_POST", columnNames = {"post_id", "user_id"})})
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post reportedPost;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User reporter;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "reported_at")
    private LocalDateTime reportedAt;

    protected Report() {
    }

    private Report(Post reportedPost, User reporter, LocalDateTime reportedAt) {
        this.reportedPost = reportedPost;
        this.reporter = reporter;
        this.reportedAt = reportedAt;
    }

    public static Report of(Post reportedPost, User reporter, LocalDateTime reportedAt) {
        return new Report(reportedPost, reporter, reportedAt);
    }

    public void reporter(User reporter) {
        this.reporter = reporter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report report = (Report) o;
        return Objects.equals(reportId, report.reportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId);
    }
}
