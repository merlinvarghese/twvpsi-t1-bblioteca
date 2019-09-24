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
    private BookService bookService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    @ApiOperation(value = "Welcome", response = Response.class, tags = "Greetings From Biblioteca")
    Response greeting() {
        return new Response("true", "Welcome Message to logged in User Successfully", welcome_Message);
    }

    @GetMapping("/books")
    @ApiOperation(value = "Get Books Listing for a Given Limit", response = List.class, tags = "List Books",
            notes="A User can Browse through the list of books.\n ")
    Response getBooksByCount(@ApiParam("Maximum Listing of Books, if max not provided: Default Value = 5 ") @Valid @RequestParam(value = "max", required = false, defaultValue = "${default.books.count}")
                               @NumberFormat(style = NumberFormat.Style.NUMBER) Long booksCount) throws NotFoundException {
        List<Book> listBook = bookService.getBooksByCount(booksCount);
        return new Response("true", "Book Listing Successful", listBook);
    }

    @GetMapping("/books/{id}")
    @ApiOperation(value = "Get Specific Book for a given Book Id", response = Response.class, tags = "Book Details")
    Response getBookById(@Valid @PathVariable("id")
                     @NumberFormat(style = NumberFormat.Style.NUMBER) Long id)
            throws NotFoundException {
        Book book = bookService.getBookById(id);
        return new Response("true", "Book Details " + id + " successfull", book);
    }

    @GetMapping("/movies")
    @ApiOperation(value = "Get Movie Listing For a Given Limit", response = Response.class, tags = "List Movies")
    Response getMoviesByCount(@ApiParam("Maximum Listing of Movies, if max not provided: Default Value = 5 ")@Valid
                                 @RequestParam(value = "max", required = false, defaultValue = "${default.movies.count}")
                                 @NumberFormat(style = NumberFormat.Style.NUMBER)
                                         Long movieCount) throws NotFoundException {
        List<Movie> listMovie =  movieService.getMoviesByCount(movieCount);
        return new Response("true", "Movie Listing", listMovie);
    }

    @GetMapping("/movies/{id}")
    @ApiOperation(value = "Get Movie Details for a specific Movie Id", response = Response.class, tags = "Movie Details")
    Response getMovieById(@Valid @PathVariable("id")
                       @NumberFormat(style = NumberFormat.Style.NUMBER)
                               Long id) throws NotFoundException {
        Movie movie =  movieService.getMovieById(id);
        return new Response("true", "Movie Details " + id + " successfull", movie);
    }

    @PostMapping("/books/{id}/operations")
    @ApiOperation(value = "Checkout the Book given the BookId", response = Messages.class, tags = "Checkout Book")
    Messages performBookOperations(@Valid @PathVariable("id")
                                  @NumberFormat(style = NumberFormat.Style.NUMBER)
                                          Long id,
                                  @RequestBody
                                          BookOperations operations) throws NotFoundException {
        return bookService.performOperations(id,operations);
    }

    @PostMapping("/movies/{id}/operations")
    @ApiOperation(value = "Checkout or Return the Movie given MovieId", response = Movie.class, tags = "Checkout/Return Movie")
    Messages performMovieOperations(@Valid
                           @PathVariable("id")
                           @NumberFormat(style = NumberFormat.Style.NUMBER)
                                   Long id,
                           @RequestBody
                                   MovieOperations operations) throws NotFoundException {
        return movieService.performOperations(id, operations);
    }
}
