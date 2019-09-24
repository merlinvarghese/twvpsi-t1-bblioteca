package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonProperty;

class Messages {
    @JsonProperty
    String message;

    public Messages(String message) {
        this.message = message;
    }

    public Messages() {

    }

    String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }
}
