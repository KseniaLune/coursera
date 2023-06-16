package com.kitten.coursera.repo;

import com.kitten.coursera.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
//    @Query(value = "select new Role (r.id, r.role) from Role r where r.role = :role")
//    Optional<Role> findByRole(@Param("role") String role);
    Optional<Role> findByRole(Role.RoleName role);
}
