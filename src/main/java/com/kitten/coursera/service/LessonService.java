package com.kitten.coursera.service;

import com.kitten.coursera.components.ResponseJson;
import com.kitten.coursera.domain.entity.LessonFile;
import com.kitten.coursera.dto.LessonDto;
import com.kitten.coursera.domain.entity.Lesson;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    Lesson create(LessonDto dto);

    Lesson findBy(UUID id);

    List<Lesson> findAllBy(UUID courseId);

    Lesson update(UUID id, LessonDto dto);

    ResponseJson delete(UUID id);

    ResponseJson uploadFile(UUID lessonId, LessonFile lessonFile);

    ResponseJson downloadFile(String fileName);

    ResponseJson showFile(String fileName);

    List<String> findAllFiles(UUID lessonId);
}
