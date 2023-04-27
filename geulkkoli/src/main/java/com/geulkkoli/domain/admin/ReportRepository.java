package com.geulkkoli.domain.admin;

import com.geulkkoli.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("select r from Report r where r.reporter.userId = :userId")
    Optional<Report> findByUserId(Long userId);

    Long countByReportedPost(Post post);

    @Query("select distinct r.reportedPost from Report r")
    List<Post> findDistinctByReportedPost();
}
