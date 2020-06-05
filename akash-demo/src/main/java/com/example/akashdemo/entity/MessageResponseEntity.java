package com.example.akashdemo.entity;

import org.springframework.lang.NonNull;

/**
 * POJO to represent an entity being returned in the web response
 */
public class MessageResponseEntity {

    private String message;

    public MessageResponseEntity() {

    }

    public MessageResponseEntity(@NonNull String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
