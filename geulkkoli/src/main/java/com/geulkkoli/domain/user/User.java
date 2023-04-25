package com.geulkkoli.domain.user;

import com.geulkkoli.application.security.LockExpiredTimeException;
import com.geulkkoli.application.security.RoleEntity;
import com.geulkkoli.domain.admin.AccountLock;
import com.geulkkoli.domain.admin.Report;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tinylog.Logger;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nick_name", nullable = false, unique = true)
    private String nickName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_No", nullable = false, unique = true)
    private String phoneNo;

    @Column(nullable = false)
    private String gender;

    /**
     * report의 필드 User reporter와 대응
     * casacde = CascadeType.AlL:: addReport할때 report.reporter()에 값을 넣으면 같이 영속성에 저장되게 하는 설정
     * report가 단일 소유가 아니기에 설정을 취소한다 추후에 post,like,comment일 때 적용하자
     */
    @OneToMany(mappedBy = "reporter")
    private Set<Report> reports = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToMany(mappedBy = "lockedUser")
    private Set<AccountLock> accountLocks = new LinkedHashSet<>();

    @Builder
    public User(String userName, String password, String nickName, String email, String phoneNo, String gender, Set<Report> reports) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.reports = reports;
    }

    public void updateUser(String userName, String nickName, String phoneNo, String gender) {
        this.userName = userName;
        this.nickName = nickName;
        this.phoneNo = phoneNo;
        this.gender = gender;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    //연관관계 편의 메서드
    public void addReport(Report report) {
        this.reports.add(report);
        report.reporter(this);
    }

    public void rock(AccountLock accountLock){
        this.accountLocks.add(accountLock);
        accountLock.addLockUser(this);
    }

    public Boolean isLock() {
        if (this.accountLocks.isEmpty()) {
            return false;
        }
        return this.accountLocks.stream()
                .map(AccountLock::getLockeExpirationDate)
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> {
                    Logger.debug("계정 잠금 기간이 설정되지 않았습니다.");
                    return new LockExpiredTimeException("계정 잠금 기간이 설정되지 않았습니다.");
                })
                .isAfter(LocalDateTime.now());
    }

    public void addRole(RoleEntity role) {
        this.role = role;
        role.addUser(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }


}
