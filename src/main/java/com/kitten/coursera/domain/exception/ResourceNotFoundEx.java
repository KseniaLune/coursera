package com.kitten.coursera.domain.exception;

public class ResourceNotFoundEx extends RuntimeException{
    public ResourceNotFoundEx(String message) {
        super(message);
    }
}
