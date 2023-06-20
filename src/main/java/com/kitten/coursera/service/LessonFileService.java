package com.kitten.coursera.service;

import com.kitten.coursera.domain.entity.LessonFile;

public interface LessonFileService {
    String addFile(LessonFile lessonFile);
    void downloadFile(String filename) throws Exception;
}
