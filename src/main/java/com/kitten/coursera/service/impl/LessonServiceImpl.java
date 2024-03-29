package com.kitten.coursera.service.impl;

import com.kitten.coursera.components.ResponseJson;
import com.kitten.coursera.domain.entity.Lesson;
import com.kitten.coursera.domain.entity.LessonFile;
import com.kitten.coursera.domain.exception.FileDownloadEx;
import com.kitten.coursera.domain.exception.FileUploadEx;
import com.kitten.coursera.domain.exception.ResourceMappingEx;
import com.kitten.coursera.domain.exception.ResourceNotFoundEx;
import com.kitten.coursera.dto.LessonDto;
import com.kitten.coursera.repo.LessonRepo;
import com.kitten.coursera.service.CourseService;
import com.kitten.coursera.service.LessonFileService;
import com.kitten.coursera.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final CourseService courseService;
    private final LessonRepo lessonRepo;
    private final LessonFileService lessonFileService;

    @Override
    @Transactional
    public Lesson create(LessonDto dto) {
        var course = courseService.findBy(dto.getCourseId());

        Lesson lesson = Lesson.builder()
            .title(dto.getTitle())
            .text(dto.getText())
            .build();
        course.addLessonToCourse(lesson);
        try {
            lessonRepo.save(lesson);
        } catch (Exception e) {
            throw new ResourceMappingEx("Error while saving lesson in DB.");
        }
        return lesson;
    }

    @Override
    public Lesson findBy(UUID id) {
        return lessonRepo.findById(id).orElseThrow(() -> new RuntimeException("Error while finding lesson by Id."));
    }

    @Override
    @Cacheable(value = "LessonService::findAllTitlesBy", key = "#courseId")
    public List<String> findAllTitlesBy(UUID courseId) {
        try {
            return courseService.findBy(courseId).getLessons().stream()
                .map(Lesson::getTitle).toList();
        } catch (Exception e) {
            throw new ResourceMappingEx("Error while finding course in DB.");
        }
    }

    @Override
    @Caching(put = {
        @CachePut(value = "LessonService::findAllTitlesBy", key = "#dto.courseId")

    })
    public Lesson update(UUID id, LessonDto dto) {
        var lesson = this.findBy(id);
        lesson.setTitle(dto.getTitle());
        lesson.setText(dto.getText());
        try {
            lesson.setCourse(courseService.findBy(dto.getCourseId()));
        } catch (Exception e) {
            throw new ResourceMappingEx("Error while finding course in DB.");
        }
        return lessonRepo.save(lesson);
    }

    @Transactional
    @Override
    public ResponseJson delete(UUID id) {
        if (lessonRepo.findById(id).isEmpty()) {
            return new ResponseJson(null, new ResourceNotFoundEx("Lesson doesn't exist."));
        }
        try {
            lessonRepo.deleteById(id);
            return new ResponseJson("Lesson is deleting", null);
        } catch (Exception e) {
            return new ResponseJson(null, new Exception(e.getMessage()));
        }

    }

    @Transactional
    @Override
    @CachePut(value = "LessonService::findAllFiles", key = "#lessonId")
    public ResponseJson uploadFile(UUID lessonId, LessonFile lessonFile) {
        try {
            Lesson lesson = this.findBy(lessonId);
            String filename = lessonFileService.addFile(lessonFile);
            lesson.getFile().add(filename);
            lessonRepo.save(lesson);
            return new ResponseJson("File was uploaded.", null);
        } catch (Exception e) {
            return new ResponseJson(null, new FileUploadEx("File wasn't uploaded: " + e.getMessage()));
        }
    }

    @Override
    public ResponseJson downloadFile(String fileName) {
        try {
            lessonFileService.downloadFile(fileName);
            return new ResponseJson("File was download.", null);
        } catch (Exception e) {
            return new ResponseJson(null, new FileDownloadEx("File wasn't download: " + e.getMessage()));
        }

    }

    @Override
    public ResponseJson showFile(String fileName) {
        try {
            String url = lessonFileService.showFile(fileName);
            return new ResponseJson(url, null);
        } catch (Exception e) {
            return new ResponseJson(null, new FileDownloadEx("Internal Error " + e.getMessage()));
        }
    }


    @Cacheable(value = "LessonService::findAllFiles", key = "#lessonId")
    @Override
    public List<String> findAllFiles(UUID lessonId) {
        Lesson lesson = this.findBy(lessonId);
        return lesson.getFile();
    }
}
