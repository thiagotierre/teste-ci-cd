package com.fiap.challenge.shared.exception.workOrder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkOrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WorkOrderNotFoundException(UUID id) {
        super("WorkOrder with ID " + id + " not found.");
    }

	public WorkOrderNotFoundException(String message) {
		super(message);
	}
}