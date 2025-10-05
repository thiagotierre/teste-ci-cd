package com.fiap.dto.vehicle;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record CreateVehicleRequest(
        @NotNull UUID customerId,
        @NotBlank @Pattern(regexp = "^[A-Z]{3}-?\\d{4}|[A-Z]{3}\\d[A-Z]\\d{2}$", message = "Formato de placa inválido.")
        String licensePlate,
        @NotBlank String brand,
        @NotBlank String model,
        @NotNull @Min(1900) Integer year
) {}