package com.kitten.coursera.service;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.entity.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {
    List<Course> readAllCourses();
    Optional<Course> findBy(UUID id);
    List<Course> findByTitleWithPrefix(String prefix);
    Course addCourse(CourseDto dto);
    Course updateCourse(UUID id, CourseDto dto);
    void deleteBy(UUID id);
}
