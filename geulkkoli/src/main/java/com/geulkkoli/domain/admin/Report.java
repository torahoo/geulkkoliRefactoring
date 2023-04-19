package com.geulkkoli.domain.admin;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
@NoArgsConstructor
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


}
