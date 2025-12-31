package com.example.coffeeshop.config;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "status", "message", "data" })
public class MessageStatus<T> {
    private String status;
    private String message;

    @Nullable
    private T data;

    public static <T> MessageStatus<T> success(String message, T data) {
        return new MessageStatus<>("T", message, data);
    }

    public static <T> MessageStatus<T> fail(String message, T data) {
        return new MessageStatus<>("F", message, data);
    }
}
