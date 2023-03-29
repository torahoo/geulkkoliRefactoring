package com.geulkkoli.respository;

import com.geulkkoli.domain.User;

import java.util.Optional;

public interface UsersRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUserId(String loginId);
}
