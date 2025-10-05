package com.fiap.challenge.vehicles.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record InputVehicleDTO(
	@NotNull(message = "O ID do cliente não pode ser nulo.")
    UUID customerId,

    @NotBlank(message = "A placa não pode estar em branco.")
    @Pattern(
        regexp = "^[A-Z]{3}-?\\d{4}|[A-Z]{3}\\d[A-Z]\\d{2}$",
        message = "Formato de placa inválido. Use ABC-1234 ou ABC1D23."
    )
    String licensePlate,

    @NotBlank(message = "A marca não pode estar em branco.")
    String brand,

    @NotBlank(message = "O modelo não pode estar em branco.")
    String model,

    @NotNull(message = "O ano não pode ser nulo.")
    @Min(value = 1900, message = "O ano do veículo parece ser muito antigo.")
    Integer year
) {}