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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final CourseService courseService;
    private final LessonRepo lessonRepo;


    @Override
    @Transactional
    public LessonDto create(LessonDto dto) {
        var course = courseService.findBy(dto.getCourseId());

        Lesson lesson = Lesson.builder()
            .title(dto.getTitle())
            .text(dto.getText())
            .build();
        course.addLessonToCourse(lesson);
        Lesson lessonSaved = lessonRepo.save(lesson);


        LessonDto newDto = LessonDto.builder()
            .title(lessonSaved.getTitle())
            .text(lessonSaved.getText())
            .courseId(lessonSaved.getCourse().getId())
            .build();
        return newDto;
}

    @Override
    public LessonDto findBy(UUID id) {
        Lesson lesson = lessonRepo.findById(id).orElseThrow(()->new RuntimeException("we have big problem"));
        LessonDto dto = LessonDto.builder()
            .title(lesson.getTitle())
            .text(lesson.getText())
            .courseId(lesson.getCourse().getId())
            .build();
        return dto;
    }

    @Override
    public List<LessonDto> findAllBy(UUID courseId) {
        Course course = courseService.findBy(courseId);
        log.info("course list:{}"+course.getLessons());

        List<LessonDto> result = course.getLessons().stream()
            .map(l->new LessonDto(l.getTitle(), l.getText(), l.getCourse().getId()))
            .collect(Collectors.toList());
        return result;
    }
    //    @Override
//
//    }
//
//    }
//        return lessonRepo.findById(id);
//    public Optional<Lesson> findById(UUID id) {
//    @Override
//
//    }
//        return lessons;
//            .collect(Collectors.toList());
//            .map(l -> new LessonDto(l.getTitle(), l.getText(), l.getCourse().getId()))
//        List<LessonDto> lessons = course.getLessons().stream()
//    public List<LessonDto> findByCourse(Course course) {
//
//    }
//        return lessons;
//            .collect(Collectors.toList());
//            .map(l -> new LessonDto(l.getTitle(), l.getText(), l.getCourse().getId()))
//        List<LessonDto> lessons = lessonRepo.findAll().stream()
//    public List<LessonDto> findAll() {
//    @Override
//


//    public Lesson update(UUID id, LessonDto dto) {
//        Lesson lesson = lessonRepo.findById(id).get();
//        lesson.setTitle(dto.getTitle());
//        lesson.setText(dto.getText());
//
//        return lessonRepo.save(lesson);
//    }
//
//    @Override
//    public void delete(UUID id) {
//        lessonRepo.deleteById(id);
//    }
}
