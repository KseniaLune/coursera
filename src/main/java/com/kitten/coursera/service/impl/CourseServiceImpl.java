package com.kitten.coursera.service.impl;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.repo.CourseRepo;
import com.kitten.coursera.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepo courseRepo;

    public List<Course> readAllCourses() {
        return courseRepo.findAll();
    }

    public Optional<Course> findBy(UUID id) {
        return courseRepo.findById(id);
    }

    public List<Course> findByTitleWithPrefix(String prefix) {
        return courseRepo.findByTitleLike(prefix + "%");
    }


    public Course addCourse(CourseDto dto) {
        var course = Course.builder()
            .title(dto.getTitle())
            .description(dto.getDescription())
            .author(dto.getAuthor())
            .build();
        return courseRepo.save(course);
    }

    public Course updateCourse(UUID id, CourseDto dto) {
        var course = findBy(id).orElseThrow();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setAuthor(dto.getAuthor());
        return courseRepo.save(course);
    }

    public void deleteBy(UUID id) {
        courseRepo.deleteById(id);
    }
}