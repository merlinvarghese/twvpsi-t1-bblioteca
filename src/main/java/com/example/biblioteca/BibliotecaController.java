package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
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

    @GetMapping("/books")
    List<Book> getBooksByCount(@Valid @RequestParam(value = "max", required = false, defaultValue = "${default.books.count}")
                               @NumberFormat(style = NumberFormat.Style.NUMBER) Long booksCount) {
        return bibliotecaService.getBooksByCount(booksCount);
    }

    @GetMapping("/books/{id}")
    Book getBookById(@Valid @PathVariable("id")
                     @NumberFormat(style = NumberFormat.Style.NUMBER) Long id)
            throws NoBookFoundException {
        return bibliotecaService.getBookById(id);
    }

    @GetMapping("/movies")
    List<Movie> getMoviesByCount(@Valid
                                 @RequestParam(value = "max", required = false, defaultValue = "${default.movies.count}")
                                 @NumberFormat(style = NumberFormat.Style.NUMBER)
                                         Long movieCount) {
        return bibliotecaService.getMoviesByCount(movieCount);
    }

    @GetMapping("/movies/{id}")
    Movie getMovieById(@Valid @PathVariable("id")
                       @NumberFormat(style = NumberFormat.Style.NUMBER)
                               Long id) throws NoBookFoundException {
        return bibliotecaService.getMovieById(id);
    }

    @PutMapping("/books")
    Messages updateCheckoutStatus(@RequestBody Book book) throws NoBookFoundException {
        return bibliotecaService.checkout(book);
    }
}
