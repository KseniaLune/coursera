package com.kitten.coursera.repo;

import com.kitten.coursera.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    Optional<Role> findByRole(Role.RoleName role);
}
