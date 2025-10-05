package com.fiap.dto.customer;

import java.util.UUID;

public record UpdateCustomerRequest(UUID id, String name, String documentNumber, String email, String phone){

}
