package com.kitten.coursera.repo;

import com.kitten.coursera.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UserRepo extends JpaRepository<AppUser, UUID> {

}
