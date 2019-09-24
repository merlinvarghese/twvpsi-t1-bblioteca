package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("ALL")
@Validated
@RestController
class BibliotecaController {
    @Autowired
    private BookService bookService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    String greeting() {
        return "Welcome to Biblioteca!";
    }

    @GetMapping("/books")
    List<Book> getBooksByCount(@Valid
                               @RequestParam(value = "max", required = false, defaultValue = "${default.books.count}")
                               @NumberFormat(style = NumberFormat.Style.NUMBER)
                                       Long booksCount) throws NotFoundException {
        return bookService.getBooksByCount(booksCount);
    }

    @GetMapping("/books/{id}")
    Book getBookById(@Valid @PathVariable("id")
                     @NumberFormat(style = NumberFormat.Style.NUMBER) Long id)
            throws NotFoundException {
        return bookService.getBookById(id);
    }

    @GetMapping("/movies")
    List<Movie> getMoviesByCount(@Valid
                                 @RequestParam(value = "max", required = false, defaultValue = "${default.movies.count}")
                                 @NumberFormat(style = NumberFormat.Style.NUMBER)
                                         Long movieCount) throws NotFoundException {
        return movieService.getMoviesByCount(movieCount);
    }

    @GetMapping("/movies/{id}")
    Movie getMovieById(@Valid @PathVariable("id")
                       @NumberFormat(style = NumberFormat.Style.NUMBER)
                               Long id) throws NotFoundException {
        return movieService.getMovieById(id);
    }

    @PatchMapping("/books/{id}/checkout")
    Messages updateCheckoutStatus(@Valid @PathVariable("id")
                                  @NumberFormat(style = NumberFormat.Style.NUMBER)
                                          Long id) throws NotFoundException {
        return bookService.checkout(id);
    }

    @PatchMapping("/books/{id}/checkin")
    Messages updateReturnStatus(@Valid @PathVariable("id")
                                @NumberFormat(style = NumberFormat.Style.NUMBER)
                                        Long id) throws NotFoundException {
        return bookService.returnBook(id);
    }

    @PostMapping("/movies/{id}/operations")
    Messages performMovieOperations(@Valid
                           @PathVariable("id")
                           @NumberFormat(style = NumberFormat.Style.NUMBER)
                                   Long id,
                           @RequestBody
                                   MovieOperations operations) throws NotFoundException {
        return movieService.performOperations(id, operations);
    }
}
