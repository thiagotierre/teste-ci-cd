package com.fiap.core.exception;

public class PasswordException extends RuntimeException {
    private String code;

    public PasswordException(String message, String code) {
    super(message);
    this.code = code;
  }
}
