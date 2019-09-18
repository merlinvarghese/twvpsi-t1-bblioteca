package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class BibliotecaService {

    @Autowired
    private BookRepository bookRepository;

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
}
