package com.geulkkoli.domain.social;

import com.geulkkoli.domain.user.User;
import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;



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
