package com.kitten.coursera.dto.mapper;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CourseMapper {

    public CourseDto mapCourseToDto(Course c) {
        return new CourseDto(c.getTitle(), c.getDescription(), c.getAuthor());
    }

    public List<CourseDto> mapCoursesToDto(List<Course> courses) {
        return courses.stream()
            .map(c -> new CourseDto(c.getTitle(), c.getDescription(), c.getAuthor()))
            .collect(Collectors.toList());
    }
}
