package com.geulkkoli.domain.user;

import com.geulkkoli.web.user.JoinForm;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickName(String nickName);

    Optional<User> findByPhoneNo(String phoneNo);



}
