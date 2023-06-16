package com.kitten.coursera.dto.mapper;

import com.kitten.coursera.dto.LessonDto;
import com.kitten.coursera.domain.entity.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LessonMapper {

    public LessonDto mapLessonToDto(Lesson lesson){
        return LessonDto.builder()
            .title(lesson.getTitle())
            .text(lesson.getText())
            .courseId(lesson.getCourse().getId())
            .build();
    }

    public List<LessonDto> mapLessonsToDto(List<Lesson> lessons){
        return lessons.stream()
            .map(lesson -> LessonDto.builder()
                .title(lesson.getTitle())
                .text(lesson.getText())
                .courseId(lesson.getCourse().getId())
                .build())
            .collect(Collectors.toList());
    }
}
