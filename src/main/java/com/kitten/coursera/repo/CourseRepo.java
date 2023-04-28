package com.kitten.coursera.repo;

import com.kitten.coursera.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course, Long> {

}
