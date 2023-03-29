package com.geulkkoli.infrastructure;

import com.geulkkoli.domain.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUserId(String loginId);
}
