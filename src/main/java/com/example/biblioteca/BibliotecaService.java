package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class BibliotecaService {

    @SuppressWarnings("unused")
    @Autowired
    private BookRepository bookRepository;

    @SuppressWarnings("unused")
    @Autowired
    private MovieRepository movieRepository;

    Book getBookById(Long id) throws NotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("No book found"));
    }

    private List<Book> getAllBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        //return books.stream().filter(book -> book.getCheckout_status().equals("AVAILABLE")).collect(Collectors.toList());
        return books;
    }

    List<Book> getBooksByCount(long count) throws NotFoundException {
        if ( count <0 ) {
            throw new NotFoundException("Invalid input");
        }
        List<Book> books = getAllBooks();
        List<Book> resultBooks = new ArrayList<>();
        for (int idx = 0; idx < books.size() && idx < count; idx++) {
            resultBooks.add(books.get(idx));
        }
        return resultBooks;
    }

    Messages checkOutBook(Long id) throws NotFoundException {
        Messages message = new Messages();
        Book book = getBookById(id);
        Messages checkoutSuccess = book.checkOut(currentBookOperation);

        return message;
    }

    Messages returnBook(Long id) throws NotFoundException {
        Messages message = new Messages();
        Book book = getBookById(id);
        Messages checkInSuccess = book.checkIn();

        return message;
    }

    private List<Movie> getAllMovies() {
        List<Movie> movies = (List<Movie>) movieRepository.findAll();
       // return movies.stream().filter(movie -> movie.getCheckout_status().equals("AVAILABLE")).collect(Collectors.toList());
        return movies;
    }

    List<Movie> getMoviesByCount(long count) throws NotFoundException {
        if ( count <0 ) {
            throw new NotFoundException("Invalid input");
        }

        List<Movie> movies = getAllMovies();
        List<Movie> resultMovies = new ArrayList<>();
        for (int i = 0; i < movies.size() && i < count; i++) {
            resultMovies.add(movies.get(i));
        }
        return resultMovies;
    }

    Movie getMovieById(Long id) throws NotFoundException {
        return movieRepository.findById(id).orElseThrow(() -> new NotFoundException("No Movie found"));
    }


//    Messages checkOutMovie(Long id) throws NotFoundException {
//        Messages message = new Messages();
//        Movie movie = getMovieById(id);
//        boolean checkoutSuccess = movie.checkOut();
//        if (checkoutSuccess) {
//            movieRepository.save(movie);
//            message.setMessage("Thank you! Enjoy the movie.");
//        } else {
//            message.setMessage("That movie is not available.");
//        }
//        return message;
//    }
//
//    Messages checkInMovie(Long id) throws NotFoundException {
//        Messages message = new Messages();
//        Movie movie = getMovieById(id);
//        boolean checkInSuccess = movie.checkIn();
//        if (checkInSuccess) {
//            movieRepository.save(movie);
//            message.setMessage("Thank you for returning the movie.");
//        } else {
//            message.setMessage("That is not a valid movie to return.");
//        }
//        return message;
//    }
}
