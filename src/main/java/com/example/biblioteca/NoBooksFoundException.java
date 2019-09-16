package com.example.biblioteca;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
class NoBooksFoundException extends Exception {
    NoBooksFoundException(String message) {
        super("No Books Found for Listing.");
    }
}
