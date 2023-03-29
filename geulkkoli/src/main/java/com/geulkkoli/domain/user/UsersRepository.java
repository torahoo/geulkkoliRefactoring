package com.geulkkoli.domain.user;

import java.util.Optional;

public interface UsersRepository {
    Users save(Users user);
    Optional<Users> findById(Long id);
}
