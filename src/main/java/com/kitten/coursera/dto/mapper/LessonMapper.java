package com.kitten.coursera.dto.mapper;

import com.kitten.coursera.dto.LessonDto;
import com.kitten.coursera.domain.entity.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LessonMapper implements Mappable<Lesson, LessonDto> {

    public LessonDto toDto(Lesson lesson){
        return LessonDto.builder()
            .title(lesson.getTitle())
            .text(lesson.getText())
            .courseId(lesson.getCourse().getId())
            .build();
    }

    public List<LessonDto> toDto(List<Lesson> lessons){
        return lessons.stream()
            .map(lesson -> LessonDto.builder()
                .title(lesson.getTitle())
                .text(lesson.getText())
                .courseId(lesson.getCourse().getId())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public Lesson toEntity(LessonDto dto) {
        return null;
    }

    @Override
    public List<Lesson> toEntity(List<LessonDto> dtos) {
        return null;
    }
}
