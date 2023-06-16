package com.kitten.coursera.repo;

import com.kitten.coursera.entity.UserToCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserToCourseRepo extends JpaRepository<UserToCourse, UUID> {

    UserToCourse findByUserIdAndCourseId(UUID userId, UUID courseId);

    void deleteByCourseId(UUID courseId);

    void deleteByUserId(UUID id);
}
