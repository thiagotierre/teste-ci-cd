package com.fiap.challenge.shared.exception.part;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PartDeletionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PartDeletionException(UUID id) {
        super("Cannot delete part with ID " + id + " because it is associated with one or more work orders.");
    }
}