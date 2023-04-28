package com.kitten.coursera.service;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.repo.CourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepo courseRepo;

    public List<Course> readAllCourses() {
        return courseRepo.findAll();
    }

    public Optional<Course> findBy(Long id) {
        return courseRepo.findById(id);
    }

    public List<Course> findByTitleWithPrefix(String prefix) {
        return courseRepo.findAll().stream()
            .filter(course -> course.getTitle().startsWith(prefix))
            .collect(Collectors.toList());
    }


    public Course addCourse(CourseDto dto) {
        var course = new Course(dto.title, dto.description, dto.author);
        return courseRepo.save(course);
    }

    public Course updateCourse(Long id, CourseDto dto) {
        var course = findBy(id).orElseThrow();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setAuthor(dto.getAuthor());
        return courseRepo.save(course);
    }

    public void deleteBy(Long id) {
        courseRepo.deleteById(id);
    }
}
