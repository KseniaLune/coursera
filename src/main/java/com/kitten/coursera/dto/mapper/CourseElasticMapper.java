package com.kitten.coursera.dto.mapper;

import com.kitten.coursera.domain.entity.Course;
import com.kitten.coursera.domain.entity.CourseElastic;
import org.springframework.stereotype.Component;

@Component
public class CourseElasticMapper {

    public CourseElastic mapToCourseElastic(Course course) {
       return CourseElastic.builder()
           .id(course.getId())
           .title(course.getTitle())
           .description(course.getDescription())
           .build();
    }

}
