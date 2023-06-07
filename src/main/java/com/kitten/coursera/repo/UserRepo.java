package com.kitten.coursera.repo;

import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;


public interface UserRepo extends JpaRepository<AppUser, UUID> {

    @Query(value = "SELECT * FROM t_user_course t WHERE t.c_usr_id=:id", nativeQuery = true)
    List<Course> findCourseByUserId(@Param("id")UUID id);
}
