package com.example.biblioteca;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class NoBookFoundException extends Exception {
  NoBookFoundException(String message) {
    super(message);
  }
}
