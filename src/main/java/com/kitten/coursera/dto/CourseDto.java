package com.kitten.coursera.dto;

import com.kitten.coursera.entity.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    @NotBlank(message = "title author has to be filled")
    @NotNull(message = "title author has to be filled")
    public String title;
    @NotBlank(message = "description has to be filled")
    @NotNull(message = "description has to be filled")
    public String description;
    @NotBlank(message = "author has to be filled")
    @NotNull(message = "author has to be filled")
    public String author;
}
