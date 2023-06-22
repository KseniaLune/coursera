package com.kitten.coursera.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@Builder
public class LessonFile implements Serializable {
    private MultipartFile file;
}
