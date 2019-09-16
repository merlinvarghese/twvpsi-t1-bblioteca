package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    List<Book> getBooksByCount(@RequestParam(value = "booksCount", required = false) @Positive Long booksCount)
            throws NoBooksFoundException {
        if (booksCount == null) {
            return bibliotecaService.getAllBooks();
        }

        return bibliotecaService.getBooksByCount(booksCount);
    }
}
