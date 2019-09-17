package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
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
   // @ResponseStatus(HttpStatus.OK)
    Book getBookById(@Valid @PathVariable("id") @Positive Long id) throws NoBookFoundException {
        return bibliotecaService.getBookById(id);
    }

}
