package com.example.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class BibliotecaServiceTest {

    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    BookRepository bookRepository;

    @Test
    void shouldGetAllBooks() throws NoBooksFoundException {
        List<Book> bookList = bibliotecaService.getAllBooks();

        assertNotEquals(0, bookList.size());
    }
}