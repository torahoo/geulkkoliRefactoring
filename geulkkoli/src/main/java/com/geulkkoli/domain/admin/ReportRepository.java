package com.geulkkoli.domain.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("select r from Report r where r.reporter.userId = :userId")
    Report findByUserId(Long userId);
}
