package com.geulkkoli.domain.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRockRepository extends JpaRepository<AccountLock, Long> {

    @Override
    <S extends AccountLock> S save(S entity);

    @Query("select a from AccountLock a where a.lockedUser.userId = :userId")
    AccountLock findByUserId(@Param("userId") Long userId);

}
