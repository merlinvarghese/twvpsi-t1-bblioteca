package com.example.biblioteca;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BibliotecaController {
    private final String WELCOME_MESSAGE = "Welcome to Biblioteca!";
    @GetMapping()

    String greeting(){
        return WELCOME_MESSAGE;
    }
}
