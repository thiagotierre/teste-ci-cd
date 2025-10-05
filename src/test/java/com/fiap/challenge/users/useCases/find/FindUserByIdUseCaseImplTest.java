package com.fiap.challenge.users.useCases.find;

import com.fiap.challenge.shared.exception.users.UserNotFoundException;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.users.repository.UserRepository;

import com.fiap.challenge.users.usecases.find.FindUserByIdUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindUserByIdUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    private FindUserByIdUseCaseImpl findUserByIdUseCase;

    @BeforeEach
    void setup() {
        findUserByIdUseCase = new FindUserByIdUseCaseImpl(userRepository);
    }

    @Test
    void shouldReturnUserWhenFound() {
        UUID userId = UUID.randomUUID();
        UserModel user = UserModel.builder()
                .id(userId)
                .name("User Test")
                .email("user@test.com")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserModel result = findUserByIdUseCase.execute(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("User Test", result.getName());
        assertEquals("user@test.com", result.getEmail());

        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            findUserByIdUseCase.execute(userId);
        });

        assertTrue(exception.getMessage().contains(userId.toString()));

        verify(userRepository).findById(userId);
    }
}
