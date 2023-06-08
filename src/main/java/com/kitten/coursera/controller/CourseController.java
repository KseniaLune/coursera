package com.kitten.coursera.controller;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.dto.mapper.CourseMapper;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.service.CourseService;
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
    private final CourseMapper courseMapper;

    @PostMapping("/createCourse")
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CourseDto dto) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> readAll() {
        List<Course>courses = courseService.readAllCourses();

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseMapper.mapCoursesToDto(courses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> readBy(@PathVariable("id") UUID id) {
        var course = courseService.findBy(id);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseMapper.mapCourseToDto(course));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Course>> getCoursesByTitlePrefix(@RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.findByTitleWithPrefix(requireNonNullElse(titlePrefix, "")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable("id") UUID id,
                                          @Valid @RequestBody CourseDto dto) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.updateCourse(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") UUID id) {
        courseService.deleteBy(id);
        return ResponseEntity.ok("Курс успешно удален");
    }

}
