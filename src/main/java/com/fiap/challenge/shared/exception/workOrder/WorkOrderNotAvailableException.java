package com.fiap.challenge.shared.exception.workOrder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkOrderNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WorkOrderNotAvailableException(UUID id) {
        super("WorkOrder with ID " + id + " is not available to assign.");
    }

	public WorkOrderNotAvailableException(String message) {
		super(message);
	}
}