package com.example.biblioteca;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("ALL")
@Api(value = "BibliotecaRestController", description = "REST APIs related to Biblioteca Application!!!!")
@Validated
@RestController
class BibliotecaController {
    private final String welcome_Message = "Welcome to Biblioteca!";

    @Autowired
    private BibliotecaService bibliotecaService;

    @GetMapping("/")
    @ApiOperation(value = "Welcome", response = Response.class, tags = "Greetings From Biblioteca")
    Response greeting() {
        return new Response("true", "Welcome Message to logged in User Successfully", welcome_Message);
    }

    @GetMapping("/books")
    @ApiOperation(value = "Get Books Listing for a Given Limit", response = Response.class, tags = "List Books",
            notes="A User can Browse through the list of books.\n ")
    Response getBooksByCount(@ApiParam("Maximum Listing of Books, if max not provided: Default Value = 5 ") @Valid @RequestParam(value = "max", required = false, defaultValue = "${default.books.count}")
                               @NumberFormat(style = NumberFormat.Style.NUMBER) Long booksCount) throws NotFoundException {
        List<Book> bookList = bibliotecaService.getBooksByCount(booksCount);
        return new Response("true", "Book Listing Successful", bookList);
    }

    @GetMapping("/books/{id}")
    @ApiOperation(value = "Get Specific Book for a given Book Id", response = Book.class, tags = "Book Details")
    Book getBookById(@Valid @PathVariable("id")
                     @NumberFormat(style = NumberFormat.Style.NUMBER) Long id)
            throws NotFoundException {
        return bibliotecaService.getBookById(id);
    }

    @GetMapping("/movies")
    @ApiOperation(value = "Get Movie Listing For a Given Limit", response = List.class, tags = "List Movies")
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
