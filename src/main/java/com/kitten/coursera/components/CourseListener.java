package com.kitten.coursera.components;

import com.kitten.coursera.entity.Course;
import com.kitten.coursera.repo.CourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CourseListener {
  //  private final CourseFinder finder;
    private final CourseRepo courseRepo;

    public List<Course> CourseByAuthor(String name){
        List<Course> allCourses = courseRepo.findAll();
        return allCourses.stream()
            .filter(course -> course.getAuthor().equals(name))
            .collect(Collectors.toList());
    }
}
