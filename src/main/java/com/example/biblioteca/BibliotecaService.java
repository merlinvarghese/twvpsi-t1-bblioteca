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

    Book getBookById(Long id) throws NoBookFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new NoBookFoundException("No book found"));
    }

    private List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    List<Book> getBooksByCount(long count) {
        List<Book> books = getAllBooks();
        List<Book> resultBooks = new ArrayList<>();
        for (int idx = 0; idx < books.size() && idx < count; idx++) {
            resultBooks.add(books.get(idx));
        }

        return resultBooks;
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
