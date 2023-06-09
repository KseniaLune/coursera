package com.kitten.coursera.service;

import com.kitten.coursera.dto.LessonDto;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.entity.Lesson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {

    Lesson create(LessonDto dto);

    Lesson findBy(UUID id);

    List<Lesson> findAllBy(UUID courseId);

    Lesson update(UUID id, LessonDto dto);

    void delete(UUID id);
}
