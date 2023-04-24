package com.geulkkoli.domain.admin;

import com.geulkkoli.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AccountLock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User lockedUser;
    private String reason;

    private LocalDateTime lockeExpirationDate;

    public AccountLock() {

    }

    private AccountLock(User user, String reason, LocalDateTime localDateTime) {
        this.lockedUser = user;
        this.reason = reason;
        this.lockeExpirationDate = localDateTime;
    }

    public static AccountLock of(User user, String reason, LocalDateTime localDateTime) {
        return new AccountLock(user, reason, localDateTime);
    }

    public void lockedUser(User user) {
        this.lockedUser = user;
    }

    public User getLockedUser() {
        return lockedUser;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getLockeExpirationDate() {
        return lockeExpirationDate;
    }
}
