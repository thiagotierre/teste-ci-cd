package com.fiap.core.domain.user;

import com.fiap.core.exception.PasswordException;
import com.fiap.core.exception.enums.ErrorCodeEnum;

public class Password {
    private final String value;

    public Password(String value) throws PasswordException {
        this.isValidPassword(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void isValidPassword(String password) {
        if (password == null) {
            throw new PasswordException(ErrorCodeEnum.USE0001.getMessage(), ErrorCodeEnum.USE0001.getCode());
        }

        if (password.length() < 8) {
            throw new PasswordException(ErrorCodeEnum.USE0002.getMessage(), ErrorCodeEnum.USE0002.getCode());
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUpper = true;
            else if (Character.isLowerCase(ch)) hasLower = true;
            else if (Character.isDigit(ch)) hasDigit = true;
            else if ("!@#$%^&*()-+".indexOf(ch) >= 0) hasSpecial = true;
        }

        if (!hasUpper) {
      throw new PasswordException(
          ErrorCodeEnum.USE0003.getMessage(), ErrorCodeEnum.USE0003.getCode());
        }
        if (!hasLower) {
      throw new PasswordException(
          ErrorCodeEnum.USE0004.getMessage(), ErrorCodeEnum.USE0004.getCode());
        }
        if (!hasDigit) {
      throw new PasswordException(
          ErrorCodeEnum.USE0005.getMessage(), ErrorCodeEnum.USE0005.getCode());
        }
        if (!hasSpecial) {
            throw new PasswordException(ErrorCodeEnum.USE0006.getMessage(), ErrorCodeEnum.USE0006.getCode());
        }
    }

}
