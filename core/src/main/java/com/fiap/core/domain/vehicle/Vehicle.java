package com.fiap.core.domain.vehicle;

import com.fiap.core.domain.customer.Customer;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    private UUID id;
    private Customer customer;
    private String licensePlate;
    private String brand;
    private String model;
    private Integer year;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}