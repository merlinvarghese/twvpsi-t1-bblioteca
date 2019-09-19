package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class BibliotecaService {

    @Autowired
    private BookRepository bookRepository;

    Book getBookById(Long id) throws NoBooksFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new NoBooksFoundException("No book found for id = " + id));
    }

    List<Book> getAllBooks() throws NoBooksFoundException {
        List<Book> books = (List<Book>) bookRepository.findAll();
        if (books.isEmpty()) {
            throw new NoBooksFoundException("No books found for listing");
        }

        return books.stream().filter(book -> book.getCheckout_status().equals("AVAILABLE")).collect(Collectors.toList());
    }

    public List<Book> getBooksByCount(long count) throws NoBooksFoundException {
        List<Book> books = getAllBooks();
        List<Book> resultBooks = new ArrayList<>();
        for (int i = 0; i < books.size() && i < count; i++) {
            resultBooks.add(books.get(i));
        }

        return resultBooks;
    }

    public Messages checkout(Book book) throws NoBooksFoundException {
        Messages message = new Messages();

        Book bookBeforeCheckoutStatusChange = getBookById(book.getId());

          boolean checkoutSuccess = book.checkout(bookBeforeCheckoutStatusChange);
          bookRepository.save(book);
          if(checkoutSuccess)
          {
              message.setMessage("Thank you! Enjoy the book");
          }
          if(!checkoutSuccess)
          {
              message.setMessage("That book is not available.");
          }
          return message;
    }
}
