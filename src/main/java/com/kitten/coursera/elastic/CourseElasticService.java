package com.kitten.coursera.elastic;

import com.kitten.coursera.domain.entity.Course;
import com.kitten.coursera.domain.entity.CourseElastic;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseElasticService {
   private final CourseElasticRepo repo;

    public Iterable<CourseElastic> getCourses() {
        return repo.findAll();
    }

    public CourseElastic createCourse(CourseElastic course) {
        return repo.save(course);
    }

    public CourseElastic updateCourse(Course updateCourse, UUID id) {
        CourseElastic course = repo.findById(id).get();
        course.setTitle(updateCourse.getTitle());
        course.setDescription(updateCourse.getDescription());
        return repo.save(course);
    }

    public void deleteCourse(UUID id) {
        repo.deleteById(id);
    }

}
