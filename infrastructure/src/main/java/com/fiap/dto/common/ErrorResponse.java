package com.fiap.dto.common;

import java.util.List;

public record ErrorResponse(String code, String message, List<ValidationError> validations){}
