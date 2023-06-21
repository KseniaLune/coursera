package com.kitten.coursera.service;

import com.kitten.coursera.domain.entity.LessonFile;

import java.util.List;
import java.util.UUID;

public interface LessonFileService {
    String addFile(LessonFile lessonFile);

    void downloadFile(String objectName);

    String showFile(String objectName);

    List<String> findFilesBy(UUID lessonId);
}
