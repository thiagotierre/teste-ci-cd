package com.fiap.core.domain.user;

import com.fiap.core.domain.Email;
import com.fiap.core.exception.EmailException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private Email email;
    private Password password;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() { }

    public User(UUID id, String name, String email, String role, String password, LocalDateTime createdAt, LocalDateTime updatedAt) throws EmailException {
        this.id = id;
        this.name = name;
        this.email = new Email(email);
        this.role = UserRole.valueOf(role);
        this.password = new Password(password);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public User(UUID id, String name, String email, String role, String password) throws EmailException {
        this.id = id;
        this.name = name;
        this.email = new Email(email);
        this.role = UserRole.valueOf(role);
        this.password = password != null ? new Password(password) : null;
        this.updatedAt = LocalDateTime.now();
    }
    public User(String name, String email, String role, String password) throws EmailException {
        this.name = name;
        this.email = new Email(email);
        this.role = UserRole.valueOf(role);
        this.password = new Password(password);
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email.getValue();
    }

    public void setEmail(String email) throws EmailException {
        this.email = new Email(email);
    }

    public String getPassword() {
        return password.getValue();
    }

    public void setPassword(String password) {
        this.password = new Password(password);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, createdAt, updatedAt);
    }
}
