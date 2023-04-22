package com.geulkkoli.application.user;

import com.geulkkoli.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByUser(User user);
}
