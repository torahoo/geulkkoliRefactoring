package com.geulkkoli.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("select f from Follow f where f.followeeId.userId = :userId")
    Optional<Follow> findByUserId(Long userId);

}
