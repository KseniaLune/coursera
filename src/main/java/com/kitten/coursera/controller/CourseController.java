package com.kitten.coursera.controller;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.service.CourseService;
import com.kitten.coursera.service.impl.CourseServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNullElse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/test")
    public String test() {
        log.info("start test method");
        return "test method";
    }

    @GetMapping("")
    public ResponseEntity<List<Course>> readAll() {
        return ResponseEntity.ok(courseService.readAllCourses());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Course>> getCoursesByTitlePrefix(@RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.findByTitleWithPrefix(requireNonNullElse(titlePrefix, "")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readBy(@PathVariable("id") UUID id) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.findBy(id).orElseThrow());
    }

    @PostMapping("/addCourse")
    public ResponseEntity<Course> addCourse(@Valid @RequestBody CourseDto dto) {
        return ResponseEntity.ok(courseService.addCourse(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable("id") UUID id,
                                          @Valid @RequestBody CourseDto dto) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(courseService.updateCourse(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") UUID id) {
        courseService.deleteBy(id);
        return ResponseEntity.ok("Курс успешно удален");
    }

}
