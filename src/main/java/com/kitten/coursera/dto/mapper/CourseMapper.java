package com.kitten.coursera.dto.mapper;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.domain.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CourseMapper implements Mappable <Course, CourseDto> {

    @Override
    public CourseDto toDto(Course c) {
        return CourseDto.builder()
            .title(c.getTitle())
            .description(c.getDescription())
            .author(c.getAuthor())
            .build();
    }

    @Override
    public List<CourseDto> toDto(List<Course> courses) {
        return courses.stream()
            .map(c -> CourseDto.builder()
                .title(c.getTitle())
                .description(c.getDescription())
                .author(c.getAuthor())
                .build())
            .collect(Collectors.toList());
    }



    @Override
    public Course toEntity(CourseDto dto) {
        return null;
    }

    @Override
    public List<Course> toEntity(List<CourseDto> dtos) {
        return null;
    }
}
