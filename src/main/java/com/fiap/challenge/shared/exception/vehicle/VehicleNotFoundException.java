package com.fiap.challenge.shared.exception.vehicle;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VehicleNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public VehicleNotFoundException(UUID id) {
		super("Vehicle with id: " + id + " not found.");
	}

	public VehicleNotFoundException(String message) {
		super(message);
	}
}
