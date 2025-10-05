package com.fiap.challenge.shared.exception.customer;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerDeletionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerDeletionException(UUID id) {
        super("Cannot delete customer with ID " + id + " because they are associated with existing vehicles or work orders.");
    }
}	