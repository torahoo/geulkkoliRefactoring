package com.geulkkoli.application.security;

import com.geulkkoli.application.security.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("select p from Permission p where p.accountStatus = :accountStatus")
    Optional<Permission> findByPermissionName(String permissionName);

}
