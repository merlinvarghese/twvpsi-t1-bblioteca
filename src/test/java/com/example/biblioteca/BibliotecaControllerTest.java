package com.example.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
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
    void expectBookDetailsForAGivenBookId() throws Exception {
        when(bibliotecaService.getBookById(1L)).thenReturn(
                (new Book(1L, "375704965","A Judgement in Stone",
                "Ruth Rendell","2000","Vintage Books USA")));

        mockMvc.perform(get("/books/{id}",1)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
                .andExpect(content().json("{\"isbn\":\"375704965\",\"title\":\"A Judgement in Stone\", \"author\":\"Ruth Rendell\"," +
                        "\"published_year\":\"2000\", \"publisher\":\"Vintage Books USA\"}"));

        verify(bibliotecaService).getBookById(1L);
    }

    @Test
    void expectNoBookFoundForAGivenBookId() throws Exception {
        when(bibliotecaService.getBookById(200L)).thenThrow(new NoBookFoundException("No Book found for book id = 200"));

        mockMvc.perform(get("/books/{id}",200)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bibliotecaService).getBookById(200L);
    }

    @Test
    void expectExceptionForNonNumericId() throws Exception {
        mockMvc.perform(get("/books/{id}","id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(bibliotecaService, never()).getBookById(1L);
    }
}