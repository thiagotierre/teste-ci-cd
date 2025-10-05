package com.fiap.service;

import com.fiap.core.domain.user.User;
import com.fiap.core.exception.EmailException;
import com.fiap.gateway.user.UserRepositoryGateway;
import com.fiap.mapper.user.UserMapper;
import com.fiap.persistence.entity.user.UserEntity;
import com.fiap.persistence.repository.user.UserEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserGatewayImplTest {
    private UserEntityRepository userEntityRepository;
    private UserMapper userMapper;
    private UserRepositoryGateway createUserGateway;

    @BeforeEach
    void setUp() {
        userEntityRepository = mock(UserEntityRepository.class);
        userMapper = mock(UserMapper.class);
        createUserGateway = new UserRepositoryGateway(userEntityRepository, userMapper);
    }

    @Test
    void shouldCreateUserSuccessfully() throws EmailException {
        var user = new User(UUID.randomUUID(),"Test User", "teste@gmail.com", "Password@123", LocalDateTime.now(), LocalDateTime.now());

        var userEntity = new UserEntity().builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .passwordHash(user.getPassword())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        when(userEntityRepository.save(userEntity)).thenReturn(userEntity);

        var result = createUserGateway.create(user);

        assertNotNull(result);
        verify(userMapper, times(1)).toEntity(user);
        verify(userEntityRepository, times(0)).save(userEntity);
    }

    @Test
    void shouldReturnFalseWhenMapperThrowsException() {
        var user = new User();
        user.setId(UUID.randomUUID());

        when(userMapper.toEntity(user)).thenThrow(new RuntimeException("Mapping error"));

        var result = createUserGateway.create(user);

        assertNotNull(result);
        verify(userMapper, times(1)).toEntity(user);
        verify(userEntityRepository, never()).save(any());
    }
}
