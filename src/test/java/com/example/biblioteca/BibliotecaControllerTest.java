package com.example.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@TestPropertySource(properties = {"default.books.count = 1"})
class BibliotecaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BibliotecaService bibliotecaService;

    @Test
    void expectWelcomeMessageOnBibliotecaInvocation() throws Exception {
        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to Biblioteca!"));
    }

    @Test
    void expectBookDetailsForGivenBookId() throws Exception {
        when(bibliotecaService.getBookById(1L)).thenReturn(
                (new Book(1L, "375704965", "A Judgement in Stone",
                        "Ruth Rendell", "2000", "Vintage Books USA")));

        mockMvc.perform(get("/books/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"isbn\":\"375704965\",\"title\":\"A Judgement in Stone\"" +
                        ", \"author\":\"Ruth Rendell\",\"published_year\":\"2000\", \"publisher\":\"Vintage Books USA\"}"));

        verify(bibliotecaService).getBookById(1L);
    }

    @Test
    void expectNoBookReturnedWhenBookWithGivenIdDoesNotExist() throws Exception {
        when(bibliotecaService.getBookById(200L)).thenThrow(new NoBookFoundException("No Book found"));

        mockMvc.perform(get("/books/{id}", 200)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bibliotecaService).getBookById(200L);
    }

    @Test
    void expectEmptyListWhenNoBooksAreAvailable() throws Exception {
        long defaultNumberOfBooks = 2L;
        List<Book> bookList = new ArrayList<>();
        when(bibliotecaService.getBooksByCount(defaultNumberOfBooks)).thenReturn(bookList);

        mockMvc.perform(get("/books?max=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(bibliotecaService).getBooksByCount(defaultNumberOfBooks);
    }

    @Test
    void expectListOfBooksByCount() throws Exception {
        List<Book> books = Collections.singletonList(
                new Book((long) 1,
                        "375704965",
                        "Harry Potter",
                        "JK Rowling",
                        "1990",
                        "Vintage Books USA")
        );
        when(bibliotecaService.getBooksByCount(1)).thenReturn(books);

        mockMvc.perform(get("/books?max=1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"isbn\":\"375704965\"," +
                        "\"title\":\"Harry Potter\"," +
                        "\"author\":\"JK Rowling\"," +
                        "\"published_year\":\"1990\"," +
                        "\"publisher\":\"Vintage Books USA\"}]"));

        verify(bibliotecaService).getBooksByCount(1);
    }

    @Test
    void expectDefaultNumberOfBooksWhenMaxNotSpecified() throws Exception {
        List<Book> books = Collections.singletonList(
                new Book((long) 1,
                        "375704965",
                        "Harry Potter",
                        "JK Rowling",
                        "1990",
                        "Vintage Books USA")
        );
        when(bibliotecaService.getBooksByCount(1)).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(bibliotecaService).getBooksByCount(1);
    }

    @Test
    void expectNotFoundExceptionWhenMaxIsNegative() throws Exception {
        when(bibliotecaService.getBooksByCount(-1)).thenThrow(NoBookFoundException.class);

        mockMvc.perform(get("/books?max=-1"))
                .andExpect(status().isNotFound());

        verify(bibliotecaService).getBooksByCount(-1);
    }
}
