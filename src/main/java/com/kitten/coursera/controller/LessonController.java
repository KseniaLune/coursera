package com.kitten.coursera.controller;

import com.kitten.coursera.components.ResponseJson;
import com.kitten.coursera.domain.entity.LessonFile;
import com.kitten.coursera.dto.LessonDto;
import com.kitten.coursera.dto.LessonFileDto;
import com.kitten.coursera.dto.mapper.LessonFileMapper;
import com.kitten.coursera.dto.mapper.LessonMapper;
import com.kitten.coursera.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final LessonFileMapper lessonFileMapper;

    @Secured({"ROLE_PROFESSOR", "ROLE_ADMIN", "ROLE_OWNER"})
    @PostMapping("/create")
    public ResponseEntity<LessonDto> addNewLesson(@RequestBody LessonDto dto) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(lessonMapper.toDto(lessonService.create(dto)));
    }

    @PreAuthorize("@customSecurityExpression.canAccessLesson(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> findLessonBy(@PathVariable("id") UUID id) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(lessonMapper.toDto(lessonService.findBy(id)));
    }

    @GetMapping()
    public ResponseEntity<List<String>> findAllTitlesBy(@RequestParam("courseId") UUID courseId) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(lessonService.findAllTitlesBy(courseId));
    }

    @Secured({"ROLE_PROFESSOR", "ROLE_ADMIN", "ROLE_OWNER"})
    @PutMapping("/{id}")
    public ResponseEntity<LessonDto> update(@PathVariable("id") UUID id, @RequestBody LessonDto dto) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(lessonMapper.toDto(lessonService.update(id, dto)));
    }

    @Secured({"ROLE_PROFESSOR", "ROLE_ADMIN", "ROLE_OWNER"})
    @PostMapping("/{id}/add_file")
    public ResponseEntity<ResponseJson> addFile(@PathVariable("id") UUID lessonId,
                                                @ModelAttribute LessonFileDto dto) {
        LessonFile lessonFile = lessonFileMapper.toEntity(dto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(lessonService.uploadFile(lessonId, lessonFile));

    }

    @PreAuthorize("@customSecurityExpression.canAccessLesson(#lessonId)")
    @GetMapping("/{file_name}/download_file")
    public ResponseEntity<ResponseJson> downloadFile(@PathVariable("file_name") String fileName,
                                                     @RequestParam("lesson_id") UUID lessonId) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(lessonService.downloadFile(fileName));
    }

    @PreAuthorize("@customSecurityExpression.canAccessLesson(#lessonId)")
    @GetMapping("/{file_name}/show_file")
    public ResponseEntity<ResponseJson> showFile(@PathVariable("file_name") String fileName,
                                                 @RequestParam("lesson_id") UUID lessonId) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(lessonService.showFile(fileName));
    }

    @PreAuthorize("@customSecurityExpression.canAccessLesson(#lessonId)")
    @GetMapping("/{id}/files")
    public ResponseEntity<List<String>> allFiles(@PathVariable("id") UUID lessonId) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(lessonService.findAllFiles(lessonId));
    }


    @Secured({"ROLE_PROFESSOR", "ROLE_ADMIN", "ROLE_OWNER"})
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseJson> delete(@PathVariable("id") UUID id) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(lessonService.delete(id));

    }

}
