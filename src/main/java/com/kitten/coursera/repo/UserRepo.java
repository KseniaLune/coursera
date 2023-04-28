package com.kitten.coursera.repo;

import com.kitten.coursera.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<AppUser, Long> {

}
