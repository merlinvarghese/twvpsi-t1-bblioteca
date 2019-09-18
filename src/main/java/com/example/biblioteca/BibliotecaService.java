package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.management.BadAttributeValueExpException;
import java.util.ArrayList;
import java.util.List;

@Service
class BibliotecaService {

    @Autowired
    private BookRepository bookRepository;

    Book getBookById(Long id) throws NoBooksFoundException, BadRequestException {
        if (id < 0) {
            throw new BadRequestException();
        }
        return bookRepository.findById(id).orElseThrow(() -> new NoBooksFoundException("No book found"));
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
