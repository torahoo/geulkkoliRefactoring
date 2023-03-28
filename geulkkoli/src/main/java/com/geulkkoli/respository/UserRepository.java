package com.geulkkoli.respository;

import com.geulkkoli.domain.Users;

import java.util.Optional;

public interface UserRepository  {
    Users save(Users user);
    Optional<Users> findById(Long id);
}
