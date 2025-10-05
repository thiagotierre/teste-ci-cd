package com.fiap.challenge.parts.useCases.find;

import com.fiap.challenge.parts.entity.PartModel;
import com.fiap.challenge.parts.repository.PartsRepository;
import com.fiap.challenge.shared.exception.part.PartNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindPartModelByIdUseCaseImpl implements FindPartModelByIdUseCase {

    private final PartsRepository partRepository;

    @Override
    public PartModel execute(UUID id) {
        return partRepository.findById(id)
                .orElseThrow(() -> new PartNotFoundException(id));
    }
}
