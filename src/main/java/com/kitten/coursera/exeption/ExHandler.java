package com.kitten.coursera.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class ExHandler {
    @ExceptionHandler
    public ResponseEntity<ApiError> noSuchElementExceptionHandler(NoSuchElementException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ApiError(ex));
    }
    @ExceptionHandler
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldValidationErrorDto> errors = ex.getFieldErrors().stream()
            .map(fieldError -> new FieldValidationErrorDto(fieldError.getField(), fieldError.getDefaultMessage()))
            .collect(toList());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new RequestFieldValidationErrorDto(errors));
    }
}
