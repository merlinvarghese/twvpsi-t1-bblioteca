package com.example.biblioteca;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BibliotecaController {

    @GetMapping()
    String greeting(){
        return "Welcome to Biblioteca!";
    }

}
