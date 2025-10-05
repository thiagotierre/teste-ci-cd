package com.fiap.config;

import com.fiap.application.gateway.customer.CustomerGateway;
import com.fiap.application.gateway.customer.DocumentNumberAvailableGateway;
import com.fiap.application.gateway.customer.EmailAvailableGateway;
import com.fiap.application.usecaseimpl.customer.*;
import com.fiap.usecase.customer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Bean
    public DocumentNumberAvailableUseCase documentNumberAvailableUseCase(DocumentNumberAvailableGateway documentNumberAvailableGateway){
        return new DocumentNumberAvailableUseCaseImpl(documentNumberAvailableGateway);
    }

    @Bean
    public EmailAvailableUseCase emailAvailableUseCase(EmailAvailableGateway emailAvailableGateway){
        return new EmailAvailableUseCaseImpl(emailAvailableGateway);
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase(DocumentNumberAvailableUseCase documentNumberAvailableUseCase, EmailAvailableUseCase emailAvailableUseCase, CustomerGateway customerGateway){
        return new CreateCustomerUseCaseImpl(documentNumberAvailableUseCase, emailAvailableUseCase, customerGateway);
    }

    @Bean
    public FindCustomerByIdUseCase findCustomerByIdUseCase(CustomerGateway customerGateway){
        return new FindCustomerByIdUseCaseImpl(customerGateway);
    }

    @Bean
    public FindCustomerByDocumentUseCase findCustomerByDocumentUseCase(CustomerGateway customerGateway){
        return new FindCustomerByDocumentUseCaseImpl(customerGateway);
    }

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(DocumentNumberAvailableUseCase documentNumberAvailableUseCase, EmailAvailableUseCase emailAvailableUseCase, CustomerGateway customerGateway){
        return new UpdateCustomerUseCaseImpl(documentNumberAvailableUseCase, emailAvailableUseCase, customerGateway);
    }

    @Bean
    public DeleteCustomerUseCase deleteCustomerUseCase(CustomerGateway customerGateway){
        return new DeleteCustomerUseCaseImpl(customerGateway);
    }
}
