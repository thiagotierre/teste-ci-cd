package com.fiap.challenge.shared.validation.CPFouCNPJ;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFouCNPJValidator implements ConstraintValidator<CPFouCNPJ, String> {

    private final CPFValidator cpfValidator = new CPFValidator();
    private final CNPJValidator cnpjValidator = new CNPJValidator();

    @Override
    public void initialize(CPFouCNPJ constraintAnnotation) {
        cpfValidator.initialize(null);
        cnpjValidator.initialize(null);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        String digitsOnly = value.replaceAll("\\D", "");

        if (digitsOnly.length() == 11) {
            return cpfValidator.isValid(digitsOnly, context);
        } else if (digitsOnly.length() == 14) {
            return cnpjValidator.isValid(digitsOnly, context);
        }

        return false;
    }
}