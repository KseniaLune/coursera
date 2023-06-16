package com.kitten.coursera.dto.mapper;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.domain.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CourseMapper {

    public CourseDto mapCourseToDto(Course c) {
        return CourseDto.builder()
            .title(c.getTitle())
            .description(c.getDescription())
            .author(c.getAuthor())
            .build();
    }

    public List<CourseDto> mapCoursesToDto(List<Course> courses) {
        return courses.stream()
            .map(c -> CourseDto.builder()
                .title(c.getTitle())
                .description(c.getDescription())
                .author(c.getAuthor())
                .build())
            .collect(Collectors.toList());
    }
}
