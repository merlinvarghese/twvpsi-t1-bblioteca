package com.example.biblioteca;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("ALL")
@Validated
@RestController
@Api(value = "BibliotecaRestController", description = "REST APIs related to Biblioteca Application!!!!")
class BibliotecaController {
    @Autowired
    private BibliotecaService bibliotecaService;

    @GetMapping("/")
    @ApiOperation(value = "Welcome", response = List.class, tags = "Greetings From Biblioteca")
    String greeting() {
        return "Welcome to Biblioteca!";
    }

    @GetMapping("/books")
    @ApiOperation(value = "Get Books List for a Given Count, default=5", response = List.class, tags = "List of Books")
    List<Book> getBooksByCount(@Valid @RequestParam(value = "max", required = false, defaultValue = "${default.books.count}")
                               @NumberFormat(style = NumberFormat.Style.NUMBER) Long booksCount) throws NotFoundException {
        return bibliotecaService.getBooksByCount(booksCount);
    }

    @GetMapping("/books/{id}")
    @ApiOperation(value = "Get specific Book for a given Book Id", response = Book.class, tags = "Book Details")
    Book getBookById(@Valid @PathVariable("id")
                     @NumberFormat(style = NumberFormat.Style.NUMBER) Long id)
            throws NotFoundException {
        return bibliotecaService.getBookById(id);
    }

    @GetMapping("/movies")
    @ApiOperation(value = "Get Movie List For a Given Count, default=5", response = List.class, tags = "List Movies")
    List<Movie> getMoviesByCount(@Valid
                                 @RequestParam(value = "max", required = false, defaultValue = "${default.movies.count}")
                                 @NumberFormat(style = NumberFormat.Style.NUMBER)
                                         Long movieCount) throws NotFoundException {
        return bibliotecaService.getMoviesByCount(movieCount);
    }

    @GetMapping("/movies/{id}")
    @ApiOperation(value = "Get Movie Details for a specific Movie Id", response = Movie.class, tags = "Movie Details")
    Movie getMovieById(@Valid @PathVariable("id")
                       @NumberFormat(style = NumberFormat.Style.NUMBER)
                               Long id) throws NotFoundException {
        return bibliotecaService.getMovieById(id);
    }

    @PatchMapping("/books/{id}/checkout")
    @ApiOperation(value = "Checkout the Book given the BookId", response = Messages.class, tags = "Checkout Book")
    Messages updateCheckoutStatus(@Valid @PathVariable("id")
                                  @NumberFormat(style = NumberFormat.Style.NUMBER)
                                          Long id) throws NotFoundException {
        return bibliotecaService.checkout(id);
    }

    @PatchMapping("/books/{id}/checkin")
    @ApiOperation(value = "Return the Book given the BookId", response = Movie.class, tags = "Return Book")
    Messages updateReturnStatus(@Valid @PathVariable("id")
                                @NumberFormat(style = NumberFormat.Style.NUMBER)
                                        Long id) throws NotFoundException {
        return bibliotecaService.returnBook(id);
    }
}
