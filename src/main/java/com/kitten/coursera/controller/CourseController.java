package com.kitten.coursera.controller;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.dto.mapper.CourseMapper;
import com.kitten.coursera.domain.entity.Course;
import com.kitten.coursera.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

    @Secured({"ROLE_ADMIN","ROLE_OWNER"})
    @PostMapping("/create")
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseDto dto) {
        var course = courseService.create(dto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseMapper.toDto(course));
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> readAll() {
        List<Course> courses = courseService.readAllCourses();

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseMapper.toDto(courses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> readBy(@PathVariable("id") UUID id) {
        var course = courseService.findBy(id);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseMapper.toDto(course));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CourseDto>> getCoursesByTitlePrefix(@RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        List<Course> courses = courseService.findByTitleWithPrefix(requireNonNullElse(titlePrefix, ""));
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseMapper.toDto(courses));
    }

    @Secured({"ROLE_PROFESSOR", "ROLE_ADMIN", "ROLE_OWNER"})
    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable("id") UUID id,
                                          @Valid @RequestBody CourseDto dto) {
        Course course = courseService.updateCourse(id, dto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseMapper.toDto(course));
    }

    @Secured({"ROLE_ADMIN","ROLE_OWNER"})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") UUID id) {
        courseService.deleteBy(id);
        return ResponseEntity.ok("Курс успешно удален");
    }

}
