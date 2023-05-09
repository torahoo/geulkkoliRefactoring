package com.geulkkoli.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Override
    <S extends Follow> S save(S entity);

    @Query("select f from Follow f where f.followeeId.userId = :userId")
    Optional<Follow> findBySelectUserId(@Param("userId") Long userId);

    @Modifying
    @Query("delete from Follow f where f.followeeId.userId = :userId")
    Optional<Follow> findByDeleteUserId(@Param("userId") Long userId);

    @Query("select count(f.followeeId) > 0 from Follow f where f.followeeId = :userId")
    Boolean existsByFolloweeId_UserId(@Param("userId") Long userId);

    @Override
    void delete(Follow entity);

}
