package com.kitten.coursera.exeption;

import lombok.Data;

import java.util.NoSuchElementException;

@Data
public class ApiError {
   private String message;

    public ApiError (NoSuchElementException e) {
        this.message = "No message found" ;
    }


}
