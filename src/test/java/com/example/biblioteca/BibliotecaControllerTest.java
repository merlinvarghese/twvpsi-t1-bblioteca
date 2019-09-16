package com.example.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("ALL")
@WebMvcTest
class BibliotecaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BibliotecaService bibliotecaService;

    @Test
    void shouldlistAllbooks() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Harry Potter", "JK Rowling", "1990"));

        when(bibliotecaService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Harry Potter\",\"author\":\"JK Rowling\",\"yearPublished\":\"1990\"}]"));

        verify(bibliotecaService).getAllBooks();
    }

    @Test
    void shouldFailToListBooksWhenNoBooksAvailable() throws Exception {
        when(bibliotecaService.getAllBooks()).thenThrow(NoBooksFoundException.class);

        mockMvc.perform(get("/books"))
                .andExpect(status().isNoContent());

        verify(bibliotecaService).getAllBooks();
    }
}
