package com.kitten.coursera.repo;

import com.kitten.coursera.domain.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepo extends JpaRepository<Course, UUID> {
    List<Course> findByTitleLike(String title);
}
