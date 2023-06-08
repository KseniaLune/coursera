package com.kitten.coursera.service;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.entity.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    Course create (CourseDto dto);

    List<Course> readAllCourses();

    Course findBy(UUID id);

    Course update(Course course);
    Course update (CourseDto courseDto);

    List<Course> findByTitleWithPrefix(String prefix);

    Course updateCourse(UUID id, CourseDto dto);
//    Course addCourse(CourseDto dto);
//    Course updateCourse(Course course);
    void deleteBy(UUID id);

}
