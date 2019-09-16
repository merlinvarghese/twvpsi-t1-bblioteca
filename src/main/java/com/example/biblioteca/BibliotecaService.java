package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BibliotecaService {

    @Autowired
    private BookRepository bookRepository;
    List<Book> getAllBooks() throws NoBooksFoundException{
        List<Book> books = (List<Book>) bookRepository.findAll();
        if (books.isEmpty()) {
            throw new NoBooksFoundException("No books found for listing");
        }

        return books;
    }
}
