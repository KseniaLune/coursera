package com.kitten.coursera.repo;

import com.kitten.coursera.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
//    @Query(value = "select new Role (r.id, r.role) from Role r where r.role = :role")
//    Optional<Role> findByRole(@Param("role") String role);
    Optional<Role> findByRole(Role.RoleName role);
}
