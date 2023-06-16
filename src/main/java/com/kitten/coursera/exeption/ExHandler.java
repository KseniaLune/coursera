package com.kitten.coursera.exeption;

import com.kitten.coursera.domain.exception.ExBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class ExHandler {
    @ExceptionHandler(NoSuchFieldException.class)
    public ResponseEntity<ApiError> noSuchElementExceptionHandler(NoSuchElementException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ApiError(ex));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldValidationErrorDto> errors = ex.getFieldErrors().stream()
            .map(fieldError -> new FieldValidationErrorDto(fieldError.getField(), fieldError.getDefaultMessage()))
            .collect(toList());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new RequestFieldValidationErrorDto(errors));
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity IllegalState(IllegalStateException ex){
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ExBody(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExBody handleRuntime(RuntimeException ex){
        return new ExBody(ex.getMessage());
    }
}
