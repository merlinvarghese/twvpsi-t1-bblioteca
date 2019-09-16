package com.example.biblioteca;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoBookFoundException extends Exception {
    public NoBookFoundException(String message) {
        super(message);
    }
}
