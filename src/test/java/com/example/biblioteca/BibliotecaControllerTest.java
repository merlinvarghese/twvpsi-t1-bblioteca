package com.example.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
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
        books.add(new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA"));

        when(bibliotecaService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"isbn\":\"375704965\"," +
                        "\"title\":\"Harry Potter\"," +
                        "\"author\":\"JK Rowling\"," +
                        "\"published_year\":\"1990\"," +
                        "\"publisher\":\"Vintage Books USA\"}]"));

        verify(bibliotecaService).getAllBooks();
    }

    @Test
    void shouldFailToListBooksWhenNoBooksAvailable() throws Exception {
        when(bibliotecaService.getAllBooks()).thenThrow(NoBooksFoundException.class);

        mockMvc.perform(get("/books"))
                .andExpect(status().isNoContent());

        verify(bibliotecaService).getAllBooks();
    }

    @Test
    void shouldListBooksByCount() throws Exception {
        List<Book> books = Arrays.asList(
                new Book((long) 1,
                        "375704965",
                        "Harry Potter",
                        "JK Rowling",
                        "1990",
                        "Vintage Books USA")
        );
        when(bibliotecaService.getBooksByCount((long) 1)).thenReturn(books);

        mockMvc.perform(get("/books?booksCount=1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"isbn\":\"375704965\"," +
                        "\"title\":\"Harry Potter\"," +
                        "\"author\":\"JK Rowling\"," +
                        "\"published_year\":\"1990\"," +
                        "\"publisher\":\"Vintage Books USA\"}]"));

        verify(bibliotecaService).getBooksByCount((long) 1);
    }

    @Test
    void shouldCheckBookCountToBePositive() throws Exception {
        mockMvc.perform(get("/books?booksCount=-1"))
                .andExpect(status().isBadRequest());

        verify(bibliotecaService, never()).getBooksByCount((long) -1);
    }
}
