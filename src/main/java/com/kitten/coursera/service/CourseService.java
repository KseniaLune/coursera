package com.kitten.coursera.service;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.domain.entity.Course;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    Course create (CourseDto dto);

    List<Course> readAllCourses();

    Course findBy(UUID id);

    Course update(Course course);

    List<Course> findByTitleWithPrefix(String prefix);

    Course updateCourse(UUID id, CourseDto dto);

    void deleteBy(UUID id);

}
