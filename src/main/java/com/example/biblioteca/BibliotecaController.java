package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@RestController
class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @GetMapping()
    String greeting() {
        return "Welcome to Biblioteca!";
    }

    @GetMapping("/books/{id}")
    Book getBookById(@Valid @PathVariable("id")
                     @Positive
                     @NumberFormat(style = NumberFormat.Style.NUMBER)
                             Long id) throws NoBookFoundException {
        return bibliotecaService.getBookById(id);
    }
}
