package com.kitten.coursera.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
