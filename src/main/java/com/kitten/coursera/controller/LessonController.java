package com.kitten.coursera.controller;

import com.kitten.coursera.dto.LessonDto;
import com.kitten.coursera.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/lesson")
public class LessonController {
//    private final LessonService lessonService;
//
//    @GetMapping
//    public ResponseEntity<List<LessonDto>> readAll(){
//        return ResponseEntity.ok().body(lessonService.findAll());
//    }

}
