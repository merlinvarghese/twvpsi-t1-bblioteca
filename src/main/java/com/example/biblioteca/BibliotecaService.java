package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class BibliotecaService {

    @SuppressWarnings("unused")
    @Autowired
    private BookRepository bookRepository;

    @SuppressWarnings("unused")
    @Autowired
    private MovieRepository movieRepository;

    Book getBookById(Long id) throws NoBookFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new NoBookFoundException("No book found"));
    }

    private List<Book> getAllBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        return books.stream().filter(book -> book.getCheckout_status().equals("AVAILABLE")).collect(Collectors.toList());
    }

    List<Book> getBooksByCount(long count) {
        List<Book> books = getAllBooks();
        List<Book> resultBooks = new ArrayList<>();
        for (int idx = 0; idx < books.size() && idx < count; idx++) {
            resultBooks.add(books.get(idx));
        }

        return resultBooks;
    }

    public Messages checkout(Book book) throws NoBookFoundException {
        Messages message = new Messages();
        Book bookBeforeCheckoutStatusChange = getBookById(book.getId());
        boolean checkoutSuccess = book.checkout(bookBeforeCheckoutStatusChange);
        bookRepository.save(book);

        if (checkoutSuccess) {
            message.setMessage("Thank you! Enjoy the book");
        }
        else {
            message.setMessage("That book is not available.");
        }

        return message;
    }

    private List<Movie> getAllMovies() {
        return (List<Movie>) movieRepository.findAll();
    }

    List<Movie> getMoviesByCount(long count) {
        List<Movie> movies = getAllMovies();
        List<Movie> resultMovies = new ArrayList<>();
        for (int i = 0; i < movies.size() && i < count; i++) {
            resultMovies.add(movies.get(i));
        }
        return resultMovies;
    }

    Movie getMovieById(Long id) throws NoBookFoundException {
        return movieRepository.findById(id).orElseThrow(() -> new NoBookFoundException("No Movie found"));
    }
}
