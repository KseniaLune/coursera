package com.kitten.coursera.exeption;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RequestFieldValidationErrorDto {
    private List<FieldValidationErrorDto> errors;
}
