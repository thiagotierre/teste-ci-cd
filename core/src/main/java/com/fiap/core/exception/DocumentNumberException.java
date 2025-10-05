package com.fiap.core.exception;

public class DocumentNumberException extends Exception {

    private String code;

    public DocumentNumberException(String message, String code) {
        super(message);
        this.code = code;
    }
}
