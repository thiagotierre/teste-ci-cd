package com.fiap.challenge.shared.exception.customer;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(UUID id) {
        super("Customer with ID " + id + " not found.");
    }

	public CustomerNotFoundException(String message) {
		super(message);
	}
}