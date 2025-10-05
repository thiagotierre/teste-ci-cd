package com.fiap.challenge.services.useCases.find;

import com.fiap.challenge.services.entity.ServiceModel;
import com.fiap.challenge.services.repository.ServiceRepository;
import com.fiap.challenge.shared.exception.serice.ServiceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindServiceModelByIdUseCaseImpl implements FindServiceModelByIdUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public ServiceModel execute(UUID id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));
    }
}