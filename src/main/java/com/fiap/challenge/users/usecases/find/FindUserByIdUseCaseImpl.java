package com.fiap.challenge.users.usecases.find;

import com.fiap.challenge.shared.exception.users.UserNotFoundException;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindUserByIdUseCaseImpl implements FindUserByIdUseCase {

    private final UserRepository userRepository;

    public FindUserByIdUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserModel execute(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
