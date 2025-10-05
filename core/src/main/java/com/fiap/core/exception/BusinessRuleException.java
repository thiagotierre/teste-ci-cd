package com.fiap.core.exception;

public class BusinessRuleException extends Exception {

    private final String code;

    public BusinessRuleException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}