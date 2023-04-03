package com.geulkkoli.domain.user;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUserId(String loginId);
    Optional<User> findByEmail(String loginId);
    Optional<User> findByNickName(String loginId);
}
