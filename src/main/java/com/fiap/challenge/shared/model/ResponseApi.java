package com.fiap.challenge.shared.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ResponseApi<T> {

    private HttpStatus status;
    private String message;
    private T data;

    public ResponseApi<T> of(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        return this;
    }

    public ResponseApi<T> of(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        return this;
    }
}
