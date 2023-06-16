package com.kitten.coursera.service.impl;

import com.kitten.coursera.domain.exception.ResourceMappingEx;
import com.kitten.coursera.domain.exception.ResourceNotFoundEx;
import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.domain.entity.Course;
import com.kitten.coursera.repo.CourseRepo;
import com.kitten.coursera.repo.UserToCourseRepo;
import com.kitten.coursera.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

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
        try {
            return courseRepo.save(course);
        } catch (Exception e){
            throw  new ResourceMappingEx("Error while saving course in DB.");
        }

    }

    @Override
    public List<Course> readAllCourses() {
        try {
            return courseRepo.findAll();
        } catch (Exception e){
            throw  new ResourceMappingEx("Error while finding courses in DB.");
        }

    }

    @Override
    public Course findBy(UUID id) {
        return courseRepo.findById(id).orElseThrow(()-> new ResourceNotFoundEx("Course not found"));
    }


    @Override
    public List<Course> findByTitleWithPrefix(String prefix) {
        return courseRepo.findByTitleLike(prefix + "%");
    }

    @Override
    public Course updateCourse(UUID id, CourseDto dto) {

        var course = this.findBy(id);
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setAuthor(dto.getAuthor());

        try {
            return courseRepo.save(course);
        } catch (Exception e){
            throw  new ResourceMappingEx("Error while updating course.");
        }
    }

    @Override
    public Course update(Course course) {
        try {
            return courseRepo.save(course);
        } catch (Exception e){
            throw  new ResourceMappingEx("Error while updating course.");
        }
    }

    @Override
    @Transactional
    public void deleteBy(UUID id) {

        try {
            userToCourseRepo.deleteByCourseId(id);
            courseRepo.deleteById(id);
        } catch (Exception e){
            throw  new ResourceMappingEx("Error while deleting course.");
        }
    }
}
