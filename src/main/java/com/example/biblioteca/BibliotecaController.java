package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @GetMapping("/books")
    List<Book> getBooksByCount(@Valid @RequestParam(value = "booksCount", required = false)
                               @Positive
                               @NumberFormat(style = NumberFormat.Style.NUMBER)
                                       Long booksCount)
            throws NoBooksFoundException {
        if (booksCount == null) {
            return bibliotecaService.getAllBooks();
        }

        return bibliotecaService.getBooksByCount(booksCount);
    }
}
