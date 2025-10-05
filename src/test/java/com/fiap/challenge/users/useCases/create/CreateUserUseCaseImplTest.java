package com.fiap.challenge.users.useCases.create;

import com.fiap.challenge.shared.exception.users.UserAlreadyExistsException;
import com.fiap.challenge.users.dto.CreateUserRequestDTO;
import com.fiap.challenge.users.dto.UserResponseDTO;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.users.enums.UserRole;
import com.fiap.challenge.users.repository.UserRepository;
import com.fiap.challenge.users.usecases.create.CreateUserUseCaseImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        String email = "teste@email.com";
        CreateUserRequestDTO dto = new CreateUserRequestDTO("User Test", email, "senha123", UserRole.ADMIN);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new UserModel()));

        UserAlreadyExistsException ex = assertThrows(UserAlreadyExistsException.class, () -> {
            createUserUseCase.execute(dto);
        });

        assertEquals("O e-mail " + email + " já está em uso.", ex.getMessage());

        verify(userRepository).findByEmail(email);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        String email = "novo@email.com";
        String rawPassword = "senha123";
        String encodedPassword = "hashed_senha123";

        CreateUserRequestDTO dto = new CreateUserRequestDTO("User Test", email, rawPassword, UserRole.ADMIN);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        UUID userId = UUID.randomUUID();

        UserModel savedUser = UserModel.builder()
                .id(userId)
                .name(dto.name())
                .email(email)
                .passwordHash(encodedPassword)
                .role(dto.role())
                .build();

        when(userRepository.save(any(UserModel.class))).thenReturn(savedUser);

        UserResponseDTO response = createUserUseCase.execute(dto).getData();

        assertNotNull(response);
        assertEquals(savedUser.getId(), response.id());
        assertEquals(savedUser.getName(), response.name());
        assertEquals(savedUser.getEmail(), response.email());
        assertEquals(savedUser.getRole(), response.role());

        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).encode(rawPassword);
        verify(userRepository).save(any(UserModel.class));
    }
}
