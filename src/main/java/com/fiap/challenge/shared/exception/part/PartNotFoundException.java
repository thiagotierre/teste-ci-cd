package com.fiap.challenge.shared.exception.part;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PartNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PartNotFoundException(UUID id) {
        super("Part with ID " + id + " not found.");
    }
}