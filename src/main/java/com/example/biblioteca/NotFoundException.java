package com.example.biblioteca;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException extends Exception {
    NotFoundException(String message) {
        super(message);
    }
}
