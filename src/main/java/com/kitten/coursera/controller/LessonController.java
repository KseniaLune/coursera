package com.kitten.coursera.controller;

import com.kitten.coursera.dto.LessonDto;
import com.kitten.coursera.dto.mapper.LessonMapper;
import com.kitten.coursera.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/lesson")
public class LessonController {
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    @PostMapping("/create")
    public ResponseEntity<LessonDto> addNewLesson(@RequestBody LessonDto dto){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lessonMapper.mapLessonToDto(lessonService.create(dto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> findLessonBy(@PathVariable("id")UUID id){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lessonMapper.mapLessonToDto(lessonService.findBy(id)));
    }
    @GetMapping()
    public ResponseEntity<List<LessonDto>>findAllLessonBy(@RequestParam("courseId")UUID courseId){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lessonMapper.mapLessonsToDto(lessonService.findAllBy(courseId)));
    }

}
