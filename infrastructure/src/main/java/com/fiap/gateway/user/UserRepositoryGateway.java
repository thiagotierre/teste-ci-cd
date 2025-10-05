package com.fiap.gateway.user;

import com.fiap.application.gateway.user.UserGateway;
import com.fiap.core.domain.user.User;
import com.fiap.core.exception.NotFoundException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.mapper.user.UserMapper;
import com.fiap.persistence.entity.user.UserEntity;
import com.fiap.persistence.repository.user.UserEntityRepository;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserRepositoryGateway implements UserGateway {

    final UserEntityRepository userEntityRepository;
    final UserMapper userMapper;

    public UserRepositoryGateway(UserEntityRepository userEntityRepository, UserMapper userMapper) {
        this.userEntityRepository = userEntityRepository;
        this.userMapper = userMapper;
    }


    @Override
    public User create(User user) {
        UserEntity userEntity = userEntityRepository.save(userMapper.toEntity(user));
        return userMapper.toDomain(userEntity);
    }

    @Override
    public User update(User user) {
        var userEntity = userEntityRepository.save(userMapper.toEntity(user));
        return userMapper.toDomain(userEntity);
    }

    @Override
    public Optional<User> findById(UUID userId) throws NotFoundException {
        var user = userEntityRepository.findById(userId).map(userMapper::toDomain).orElse(null);
        if(Objects.isNull(user)) throw new NotFoundException(ErrorCodeEnum.USE0007.getMessage(), ErrorCodeEnum.USE0007.getCode());;
        return Optional.of(user);
    }

    @Override
    public void delete(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        userEntityRepository.delete(userEntity);

    }
}
