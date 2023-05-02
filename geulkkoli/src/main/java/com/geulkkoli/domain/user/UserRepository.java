package com.geulkkoli.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    @Query("select u from User u where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("select u from User u where u.userName = :userName and u.phoneNo = :phoneNo")
    Optional<User> findByUserNameAndPhoneNo(@Param("userName") String userName, @Param("phoneNo") String phoneNo);

    @Query("select u from User u where u.email = :email and u.userName = :userName and u.phoneNo = :phoneNo")
    Optional<User> findByEmailAndUserNameAndPhoneNo(@Param("email") String email, @Param("userName") String userName, @Param("phoneNo") String phoneNo);

    @Query("select u from User u where u.nickName = :nickName")
    Optional<User> findByNickName(@Param("nickName") String nickName);

    Optional<User> findByPhoneNo(String phoneNo);

}
