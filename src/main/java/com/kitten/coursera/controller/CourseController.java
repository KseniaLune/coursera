package com.kitten.coursera.controller;

import com.kitten.coursera.components.ResponseJson;
import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.dto.mapper.CourseMapper;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
            .contentType(MediaType.APPLICATION_JSON)
            .body(courseMapper.mapCourseToDto(course));
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> readAll() {
        List<Course> courses = courseService.readAllCourses();

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(courseMapper.mapCoursesToDto(courses));

    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> readBy(@PathVariable("id") UUID id) {
        var course = courseService.findBy(id);

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(courseMapper.mapCourseToDto(course));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CourseDto>> getCoursesByTitlePrefix(@RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        List<Course> courses = courseService.findByTitleWithPrefix(requireNonNullElse(titlePrefix, ""));
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(courseMapper.mapCoursesToDto(courses));
    }

    @Secured({"ROLE_PROFESSOR", "ROLE_ADMIN", "ROLE_OWNER"})
    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable("id") UUID id,
                                          @Valid @RequestBody CourseDto dto) {
        Course course = courseService.updateCourse(id, dto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(courseMapper.mapCourseToDto(course));
    }

    @Secured({"ROLE_ADMIN","ROLE_OWNER"})
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseJson> deleteCourse(@PathVariable("id") UUID id) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(courseService.deleteBy(id));
    }

}
