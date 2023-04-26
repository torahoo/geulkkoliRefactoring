package com.geulkkoli.domain.user;

import com.geulkkoli.application.security.LockExpiredTimeException;
import com.geulkkoli.application.security.Role;
import com.geulkkoli.application.security.RoleEntity;
import com.geulkkoli.domain.admin.AccountLock;
import com.geulkkoli.domain.admin.Report;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.comment.Comments;
import com.geulkkoli.domain.favorites.Favorites;
import com.geulkkoli.web.post.dto.AddDTO;
import com.geulkkoli.web.post.dto.EditDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Report> reports = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToMany(mappedBy = "lockedUser")
    private Set<AccountLock> accountLocks = new LinkedHashSet<>();

    //게시글의 유저 매핑
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new LinkedHashSet<>();

    //댓글의 유저 매핑
    @OneToMany(mappedBy = "user")
    private Set<Comments> comments = new LinkedHashSet<>();

    //좋아요의 유저 매핑
    @OneToMany(mappedBy = "user")
    private Set<Favorites> favorites = new LinkedHashSet<>();

    @Builder
    public User(String userName, String password, String nickName, String email, String phoneNo, String gender) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.gender = gender;
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

    public Post writePost(AddDTO addDTO) {
        Post post = new Post(addDTO, this);
        this.posts.add(post);
        return post;
    }

    public Post deletePost(Long postId) {
        Post deltePost = findPost(postId);
        posts.remove(deltePost);
        return deltePost;
    }

    private Post findPost(Long postId) {
        return this.posts.stream()
                .filter(post -> post.getPostId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new NoSuchPostException("해당 게시글이 없습니다."));
    }

    public Post editPost(Long postId, EditDTO editDTO) {
        Post post = this.posts.stream()
                .filter(p -> p.getPostId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new NoSuchPostException("해당 게시글이 없습니다."));

        post.changePostBody(editDTO.getPostBody());
        post.changeTitle(editDTO.getTitle());
        return post;
    }

    public Report writeReport(Post post, String reason) {
        Report report = Report.of(post, this, LocalDateTime.now(), reason);
        this.reports.add(report);
        return report;
    }


    public Report deleteReport(Long reportId) {
        Report deletReport = findReport(reportId);
        reports.remove(deletReport);
        return deletReport;
    }

    private Report findReport(Long reportId) {
        return this.reports.stream()
                .filter(report -> report.getReportId().equals(reportId))
                .findFirst()
                .orElseThrow(() -> new NoSuchReportException("해당 신고가 없습니다."));
    }

    /**
     * @param reason             잠김 이유가 들어옵니다.
     * @param lockExpirationDate 만료 날짜가 들어옵니다.
     */
    public AccountLock rock(String reason, LocalDateTime lockExpirationDate) {
        AccountLock accountLock = AccountLock.of(this, reason, lockExpirationDate);
        this.accountLocks.add(accountLock);
        return accountLock;
    }

    //계정이 잠금 여부를 확인하는 메서드 입니다.
    public Boolean isLock() {
        if (CollectionUtils.isEmpty(this.accountLocks)) {
            return false;
        }

        checkLockExpiredDate();

        return this.accountLocks.stream()
                .map(AccountLock::getLockExpirationDate)
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new LockExpiredTimeException("계정 잠금 기간이 설정되지 않았습니다."))
                .isAfter(LocalDateTime.now());
    }

    private void checkLockExpiredDate() {
        for (AccountLock accountLock : this.accountLocks) {
            if (Objects.isNull(accountLock.getLockExpirationDate())) {
                throw new LockExpiredTimeException("계정 잠금 기간이 설정되지 않았습니다.");
            }
        }
    }


    public RoleEntity hasRole(Role role) {
        RoleEntity roleEntity = RoleEntity.of(role,this);
        this.role = roleEntity;

        return roleEntity;
    }

    public RoleEntity getRole() {
        return role;
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



