package com.kitten.coursera.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDto {
    private String title;
    private String text;
    private UUID courseId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> files;

    public LessonDto(UUID courseId){
        this.courseId = courseId;
    }
}
