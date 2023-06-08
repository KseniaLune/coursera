package com.kitten.coursera.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDto {
    private String title;
    private String text;
    private UUID courseId;

    public LessonDto(UUID courseId){
        this.courseId = courseId;
    }
}
