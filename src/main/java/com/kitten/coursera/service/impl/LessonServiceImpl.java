package com.kitten.coursera.service.impl;

import com.kitten.coursera.components.ResponseJson;
import com.kitten.coursera.domain.exception.ResourceMappingEx;
import com.kitten.coursera.domain.exception.ResourceNotFoundEx;
import com.kitten.coursera.dto.LessonDto;
import com.kitten.coursera.domain.entity.Course;
import com.kitten.coursera.domain.entity.Lesson;
import com.kitten.coursera.domain.exception.ExBody;
import com.kitten.coursera.repo.LessonRepo;
import com.kitten.coursera.service.CourseService;
import com.kitten.coursera.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        } catch (Exception e){
            throw  new ResourceMappingEx("Error while saving lesson in DB.");
        }
        return lesson;
    }

    @Override
    public Lesson findBy(UUID id) {
        return lessonRepo.findById(id).orElseThrow(() -> new RuntimeException("Error while finding lesson by Id."));
    }

    @Override
    public List<Lesson> findAllBy(UUID courseId) {
        try {
            return courseService.findBy(courseId).getLessons();
        } catch (Exception e){
            throw  new ResourceMappingEx("Error while finding course in DB.");
        }
    }

    @Override
    public Lesson update(UUID id, LessonDto dto) {
        var lesson = this.findBy(id);
        lesson.setTitle(dto.getTitle());
        lesson.setText(dto.getText());
        try {
            lesson.setCourse(courseService.findBy(dto.getCourseId()));
        } catch (Exception e){
            throw  new ResourceMappingEx("Error while finding course in DB.");
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
}
