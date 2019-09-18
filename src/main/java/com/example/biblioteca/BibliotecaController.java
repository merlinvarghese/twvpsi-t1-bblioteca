package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController

class BibliotecaController {
    @Autowired
    private BibliotecaService bibliotecaService;

    @GetMapping("/")
    String greeting() {
        return "Welcome to Biblioteca!";
    }

    @GetMapping("/books/{id}")
    Book getBookById(@PathVariable("id")
                     @NumberFormat(style = NumberFormat.Style.NUMBER) Long id)
                                    throws NoBooksFoundException, BadRequestException {
        return bibliotecaService.getBookById(id);
    }

    @GetMapping("/books")
    List<Book> getBooksByCount(@Valid @RequestParam(value = "max", required = false, defaultValue = "${books.count}")
                               @NumberFormat(style = NumberFormat.Style.NUMBER) Long booksCount){
        return bibliotecaService.getBooksByCount(booksCount);
    }
}
