package com.kitten.coursera.service;

import com.kitten.coursera.dto.LessonDto;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.entity.Lesson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
//    List<LessonDto> findAll();
//    public List<LessonDto> findByCourse(Course course);
//    Optional<Lesson> findById(UUID id);
LessonDto create(LessonDto dto);

    LessonDto findBy(UUID id);

    List<LessonDto> findAllBy(UUID courseId);
//    Lesson update(UUID id, LessonDto dto);
//    void delete(UUID id);
}
