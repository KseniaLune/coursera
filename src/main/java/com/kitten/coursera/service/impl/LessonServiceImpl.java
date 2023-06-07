package com.kitten.coursera.service.impl;

import com.kitten.coursera.dto.LessonDto;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.entity.Lesson;
import com.kitten.coursera.repo.LessonRepo;
import com.kitten.coursera.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepo lessonRepo;

//    @Override
//    public List<LessonDto> findAll() {
//        List<LessonDto> lessons = lessonRepo.findAll().stream()
//            .map(l -> new LessonDto(l.getTitle(), l.getText(), l.getCourse().getId()))
//            .collect(Collectors.toList());
//        return lessons;
//    }
//
//    public List<LessonDto> findByCourse(Course course) {
//        List<LessonDto> lessons = course.getLessons().stream()
//            .map(l -> new LessonDto(l.getTitle(), l.getText(), l.getCourse().getId()))
//            .collect(Collectors.toList());
//        return lessons;
//    }
//
//    @Override
//    public Optional<Lesson> findById(UUID id) {
//        return lessonRepo.findById(id);
//    }
//
//    @Override
//    public Lesson create(LessonDto dto, Course course) {
//        Lesson lesson = Lesson.builder()
//            .title(dto.getTitle())
//            .text(dto.getText())
//            .course(course)
//            .build();
//
//        return lessonRepo.save(lesson);
//    }
//
//    @Override
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
