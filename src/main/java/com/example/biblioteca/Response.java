package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    @JsonProperty
    private String success;

    @JsonProperty
    private String message;

    @JsonProperty
    private Object data;

    public Response() {}

    public Response(String success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
