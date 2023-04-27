package com.geulkkoli.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.nickName = :nickName")
    Optional<User> findByNickName(@Param("nickName") String nickName);

    Optional<User> findByPhoneNo(String poneNo);

}
