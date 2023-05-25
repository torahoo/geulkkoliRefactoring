package com.geulkkoli.domain.social;

import com.geulkkoli.domain.user.User;
import lombok.Builder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

// TODO: 2023/05/23

/**
 * social_info_id bigint primary key auto_increment,
 * user_id bigint not null,
 * social_id varchar(255) not null,
 * social_provider_name varchar(255) not null,
 * social_connect_date TIMESTAMP Null,
 * constraint fk_social_info_user foreign key (user_id) references users (user_id),
 * constraint unique_social_info unique (social_id, social_provider_name)
 */
@Entity
@Table(name = "social_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"social_id", "social_type"})})
public class SocialInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_info_id")
    private Long socialInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, name = "social_id")
    private String socialId;

    @Column(nullable = false, name = "social_type")
    private String socialType;

    @Column
    private LocalDateTime socialConnectDate;

    public SocialInfo() {
    }

    @Builder
    public SocialInfo(User user, String socialId, String socialType, LocalDateTime socialConnectDate) {
        this.user = user;
        this.socialId = socialId;
        this.socialType = socialType;
        this.socialConnectDate = socialConnectDate;
    }

    public User getUser() {
        return user;
    }

    public Long getSocialInfoId() {
        return socialInfoId;
    }

    public String getSocialId() {
        return socialId;
    }

    public String getSocialType() {
        return socialType;
    }

    public LocalDateTime getSocialConnectDate() {
        return socialConnectDate;
    }
}
