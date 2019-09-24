package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Operations {

    @JsonProperty
    String type;

    public String getType() {
        return type;
    }
}
