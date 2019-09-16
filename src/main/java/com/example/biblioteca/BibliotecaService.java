package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class BibliotecaService {

    @Autowired
    BookRepository bookRepository;

    Book getBookById(Long id) throws NoBookFoundException {
        return bookRepository.findById(id).orElseThrow(()-> new NoBookFoundException("No book found for id = " + id));
    }
}
