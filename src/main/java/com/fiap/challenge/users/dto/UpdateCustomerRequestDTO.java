package com.fiap.challenge.users.dto;

import com.fiap.challenge.shared.validation.CPFouCNPJ.CPFouCNPJ;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateCustomerRequestDTO(
	@NotBlank(message = "O nome não pode estar em branco.")
    String name,

    @NotBlank(message = "O CPF/CNPJ não pode estar em branco.")
    @CPFouCNPJ
    String cpfCnpj,

    @Pattern(regexp = "^(\\d{10}|\\d{11})$", message = "O telefone deve conter 10 ou 11 dígitos.")
    String phone,

    @NotBlank(message = "O email não pode estar em branco.")
    @Email(message = "Formato de e-mail inválido.")
    String email
) {}