package com.example.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BibliotecaServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BibliotecaService bibliotecaService;

    @Test
    void expectBookDetailsForAGivenBookId() throws Exception {
        long bookId = 1L;

        Book book = bookRepository.findById(bookId).orElse(null);
        Book fetchedBook = bibliotecaService.getBookById(bookId);

        assertEquals(book, fetchedBook);
    }
}