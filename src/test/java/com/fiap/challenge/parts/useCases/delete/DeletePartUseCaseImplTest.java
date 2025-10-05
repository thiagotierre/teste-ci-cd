package com.fiap.challenge.parts.useCases.delete;

import com.fiap.challenge.parts.repository.PartsRepository;
import com.fiap.challenge.shared.exception.part.PartDeletionException;
import com.fiap.challenge.shared.exception.part.PartNotFoundException;
import com.fiap.challenge.workOrders.repository.WorkOrderPartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePartUseCaseImplTest {

    @Mock
    private PartsRepository partsRepository;

    @Mock
    private WorkOrderPartRepository workOrderPartRepository;

    @InjectMocks
    private DeletePartUseCaseImpl useCase;

    @Test
    void shouldDeletePartWhenExistsAndNotUsedInWorkOrders() {
        UUID id = UUID.randomUUID();

        when(partsRepository.existsById(id)).thenReturn(true);
        when(workOrderPartRepository.existsByPartId(id)).thenReturn(false);

        useCase.execute(id);

        verify(partsRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldThrowWhenPartDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(partsRepository.existsById(id)).thenReturn(false);

        assertThrows(PartNotFoundException.class, () -> useCase.execute(id));

        verify(partsRepository, never()).deleteById(any());
        verify(workOrderPartRepository, never()).existsByPartId(any());
    }

    @Test
    void shouldThrowWhenPartIsUsedInWorkOrders() {
        UUID id = UUID.randomUUID();

        when(partsRepository.existsById(id)).thenReturn(true);
        when(workOrderPartRepository.existsByPartId(id)).thenReturn(true);

        assertThrows(PartDeletionException.class, () -> useCase.execute(id));

        verify(partsRepository, never()).deleteById(any());
    }
}
