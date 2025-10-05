package com.fiap.core.domain.customer;

import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.core.exception.DocumentNumberException;

public class DocumentNumber {

    private final String value;

    private DocumentNumber(String documentNumber) {
        this.value = documentNumber;
    }

    /**
     * Cria um DocumentNumber validando CPF ou CNPJ.
     * @param documentNumber CPF ou CNPJ (pode conter . - /)
     * @throws DocumentNumberException se inválido
     */
    public static DocumentNumber of(String documentNumber) throws DocumentNumberException {
        String documentCleaned = validateAndClean(documentNumber);

        return new DocumentNumber(documentCleaned);
    }

    /**
     * Cria um DocumentNumber sem validar.
     * @param documentNumber CPF ou CNPJ (pode conter . - /)
     */
    public static DocumentNumber fromPersistence(String documentNumber) {
        return new DocumentNumber(documentNumber);
    }

    /**
     * Retorna o valor limpo do documento (apenas números)
     */
    public String getValue() {
        return value;
    }

    /**
     * Valida e limpa o documento
     */
    private static String validateAndClean(String documentNumber) throws DocumentNumberException {
        if (documentNumber == null || documentNumber.isBlank()) {
            throw new DocumentNumberException(ErrorCodeEnum.CAD0001.getMessage(), ErrorCodeEnum.CAD0001.getCode());
        }

        String cleaned = documentNumber.replaceAll("\\D", "");

        boolean valid;
        if (cleaned.length() == 11) {
            valid = isCpfValid(cleaned);
        } else if (cleaned.length() == 14) {
            valid = isCnpjValid(cleaned);
        } else {
            valid = false;
        }

        if (!valid) {
            throw new DocumentNumberException(ErrorCodeEnum.CAD0001.getMessage(), ErrorCodeEnum.CAD0001.getCode());
        }

        return cleaned;
    }

    private static boolean isCpfValid(String cpf) {
        if (cpf.chars().distinct().count() == 1) return false;

        int d1 = calculateDigit(cpf, new int[]{10,9,8,7,6,5,4,3,2});
        int d2 = calculateDigit(cpf, new int[]{11,10,9,8,7,6,5,4,3,2});

        return (cpf.charAt(9) - '0' == d1) && (cpf.charAt(10) - '0' == d2);
    }

    private static boolean isCnpjValid(String cnpj) {
        if (cnpj.chars().distinct().count() == 1) return false;

        int d1 = calculateDigit(cnpj, new int[]{5,4,3,2,9,8,7,6,5,4,3,2});
        int d2 = calculateDigit(cnpj, new int[]{6,5,4,3,2,9,8,7,6,5,4,3,2});

        return (cnpj.charAt(12) - '0' == d1) && (cnpj.charAt(13) - '0' == d2);
    }

    /**
     * Calcula o dígito verificador com base nos pesos fornecidos
     */
    private static int calculateDigit(String number, int[] weights) {
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += (number.charAt(i) - '0') * weights[i];
        }
        int digit = 11 - (sum % 11);
        return digit >= 10 ? 0 : digit;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DocumentNumber other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
