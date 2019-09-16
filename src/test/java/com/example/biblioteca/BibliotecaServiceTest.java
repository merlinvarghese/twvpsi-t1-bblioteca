package com.example.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BibliotecaServiceTest {
    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    BookRepository bookRepository;

    @Test
    void shouldListAllBooks() throws NoBooksFoundException {
        bookRepository.deleteAll();
        Book book = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA");
        bookRepository.save(book);

        List<Book> bookList = bibliotecaService.getAllBooks();

        assertEquals(1, bookList.size());
    }

    @Test
    void shouldFailWhenBooksNotAvailableForListing() throws NoBooksFoundException {
        bookRepository.deleteAll();

        assertThrows(NoBooksFoundException.class, () -> bibliotecaService.getAllBooks());
    }

    @Test
    void shouldGetBooksOfGivenCount() throws NoBooksFoundException {
        bookRepository.deleteAll();
        Book book1 = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA");
        Book book2 = new Book((long) 2,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA");
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> bookList = bibliotecaService.getBooksByCount(1);

        assertEquals(1, bookList.size());
    }
}