package com.example.biblioteca;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class NoBooksFoundException extends Exception {
    NoBooksFoundException(String message) {
        super(message);
    }
}
