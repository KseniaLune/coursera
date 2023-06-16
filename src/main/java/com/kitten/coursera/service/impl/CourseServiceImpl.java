package com.kitten.coursera.service.impl;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.repo.CourseRepo;
import com.kitten.coursera.repo.UserToCourseRepo;
import com.kitten.coursera.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepo courseRepo;
    private final UserToCourseRepo userToCourseRepo;

    @Override
    public Course create(CourseDto dto) {
        Course course = Course.builder()
            .title(dto.getTitle())
            .description(dto.getDescription())
            .author(dto.getAuthor())
            .build();
       return courseRepo.save(course);
    }

    @Override
    public List<Course> readAllCourses() {
        return courseRepo.findAll();
    }

    @Override
    public Course findBy(UUID id) {
        return courseRepo.findById(id).orElseThrow(()-> new RuntimeException("Course not found"));
    }


    @Override
    public List<Course> findByTitleWithPrefix(String prefix) {
        return courseRepo.findByTitleLike(prefix + "%");
    }

    @Override
    public Course updateCourse(UUID id, CourseDto dto) {
        var course = findBy(id);
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setAuthor(dto.getAuthor());
        return courseRepo.save(course);
    }

    @Override
    public Course update(Course course) {
        return courseRepo.save(course);
    }

    @Override
    @Transactional
    public void deleteBy(UUID id) {
        userToCourseRepo.deleteByCourseId(id);
        courseRepo.deleteById(id);
    }
}
