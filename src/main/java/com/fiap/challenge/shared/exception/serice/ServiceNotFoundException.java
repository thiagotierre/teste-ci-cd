package com.fiap.challenge.shared.exception.serice;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ServiceNotFoundException(UUID id) {
		super("Service with id: " + id + " not found.");
	}
}
