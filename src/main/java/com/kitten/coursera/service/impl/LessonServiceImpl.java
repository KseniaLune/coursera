package com.kitten.coursera.service.impl;

import com.kitten.coursera.dto.LessonDto;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.entity.Lesson;
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
        lessonRepo.save(lesson);

        return lesson;
    }

    @Override
    public Lesson findBy(UUID id) {
        return lessonRepo.findById(id).orElseThrow(() -> new RuntimeException("we have big problem"));
    }

    @Override
    public List<Lesson> findAllBy(UUID courseId) {
        Course course = courseService.findBy(courseId);
        log.info("course list:{}" + course.getLessons());

        return course.getLessons();
    }

    @Override
    public Lesson update(UUID id, LessonDto dto) {
        var lesson = this.findBy(id);
        lesson.setTitle(dto.getTitle());
        lesson.setText(dto.getText());
        lesson.setCourse(courseService.findBy(dto.getCourseId()));

        return lessonRepo.save(lesson);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        lessonRepo.deleteById(id);
    }
}
