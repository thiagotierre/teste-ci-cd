package com.fiap.core.exception.enums;

public enum ErrorCodeEnum {

    CAD0001("Documento Inválido", "CAD-0001"),
    CAD0002("Documento já cadastrado!", "CAD-0002"),
    CAD0003("Email já cadastrado!", "CAD-0003"),
    CAD0004("Erro na criação do cliente", "CAD-0004"),

    USE0001("A senha não pode ser nula", "USE-0001"),
    USE0002("A senha deve ter pelo menos 8 caracteres", "USE-00022"),
    USE0003("A senha deve conter pelo menos uma letra maiúscula", "USE-0003"),
    USE0004("A senha deve conter pelo menos uma letra minúscula", "USE-0004"),
    USE0005("A senha deve conter pelo menos um número", "USE-0005"),
    USE0006("A senha deve conter pelo menos um caractere especial (!@#$%^&*()-+)", "USE-0006"),
    USE0007("Usuário não encontrado", "USE-0007"),
    USE0008("Erro na criação do usuário", "USE-0008"),

    CUST0001("Cliente não encontrado", "CUST-0001");

    private String message;
    private String code;

    ErrorCodeEnum(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
