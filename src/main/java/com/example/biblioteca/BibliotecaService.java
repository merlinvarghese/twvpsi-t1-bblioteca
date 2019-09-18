package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class BibliotecaService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MovieRepository movieRepository;

    Book getBookById(Long id) throws NoBookFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new NoBookFoundException("No book found"));
    }

    private List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    public List<Book> getBooksByCount(long count) {
        List<Book> books = getAllBooks();
        List<Book> resultBooks = new ArrayList<>();
        for (int i = 0; i < books.size() && i < count; i++) {
            resultBooks.add(books.get(i));
        }
        return resultBooks;
    }

    private List<Movie> getAllMovies(){
        return (List<Movie>) movieRepository.findAll();
    }

     List<Movie>getMoviesByCount(long count){
        List<Movie> movies = getAllMovies();
        List<Movie> moviesAddedToResp = new ArrayList<>();
        for (int i = 0; i < movies.size() && i < count; i++) {
            moviesAddedToResp.add(movies.get(i));
        }
        return moviesAddedToResp;
    }

}
