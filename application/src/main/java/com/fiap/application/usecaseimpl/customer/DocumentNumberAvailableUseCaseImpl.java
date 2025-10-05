package com.fiap.application.usecaseimpl.customer;

import com.fiap.application.gateway.customer.DocumentNumberAvailableGateway;
import com.fiap.usecase.customer.DocumentNumberAvailableUseCase;

public class DocumentNumberAvailableUseCaseImpl implements DocumentNumberAvailableUseCase {

    private final DocumentNumberAvailableGateway documentNumberAvailableGateway;

    public DocumentNumberAvailableUseCaseImpl(DocumentNumberAvailableGateway documentNumberAvailableGateway) {
        this.documentNumberAvailableGateway = documentNumberAvailableGateway;
    }

    @Override
    public Boolean documentNumberAvailable(String documentNumber) {
        return documentNumberAvailableGateway.documentNumberAvailable(documentNumber);
    }
}
