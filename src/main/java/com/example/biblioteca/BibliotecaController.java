package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class BibliotecaController {
    private final String WELCOME_MESSAGE = "Welcome to Biblioteca!";

    @Autowired
    private BibliotecaService bibliotecaService;

    @GetMapping()
    String greeting(){
        return WELCOME_MESSAGE;
    }

    @GetMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    Book getBookById(@PathVariable("id") Long id) throws NoBookFoundException {
        return bibliotecaService.getBookById(id);
    }

}
