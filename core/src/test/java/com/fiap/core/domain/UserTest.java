package com.fiap.core.domain;

import com.fiap.core.domain.user.User;
import com.fiap.core.exception.EmailException;
import com.fiap.core.exception.PasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  @Test
  void throwsExceptionUserEmail() {
    User user = new User();
    assertThrows(
        EmailException.class,
        () -> {
          user.setEmail("teste.com");
        });
  }

  @Test
  void setUserEmailValid() {
    User user = new User();
    assertDoesNotThrow(
        () -> {
          user.setEmail("thiago@bol.com");
        });
  }

  @Test
  void throwsExceptionForPasswordWithoutACapitalLetter() {
    User user = new User();
    assertThrows(
        PasswordException.class,
        () -> {
          user.setPassword("senha123");
        });
  }

  @Test
  void throwsExceptionForPasswordWithoutANumber() {
    User user = new User();
    assertThrows(
        PasswordException.class,
        () -> {
          user.setPassword("Senha");
        });
  }

  @Test
  void throwsExceptionForPasswordWithoutAMinusculeLetter() {
    User user = new User();
    assertThrows(
        PasswordException.class,
        () -> {
          user.setPassword("SENHA123");
        });
  }

  @Test
  void throwsExceptionForPasswordWithLessThan8Characters() {
    User user = new User();
    assertThrows(
        PasswordException.class,
        () -> {
          user.setPassword("S1a");
        });
  }

  @Test
  void throwsExceptionForPasswordWithoutSpecialCharacters() {
    User user = new User();
    assertThrows(
        PasswordException.class,
        () -> {
          user.setPassword("Senha123");
        });
  }

  @Test
  void setUserPasswordValid() {
    User user = new User();
    assertDoesNotThrow(
        () -> {
          user.setPassword("Senha@123");
        });
  }
}
