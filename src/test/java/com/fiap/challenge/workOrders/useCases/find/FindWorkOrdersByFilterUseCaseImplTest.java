package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.workOrders.dto.WorkOrderFilterDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderResumeDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.mapper.WorkOrderMapper;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindWorkOrdersByFilterUseCaseImplTest {

    @InjectMocks
    private FindWorkOrdersByFilterUseCaseImpl useCase;

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private WorkOrderMapper workOrderMapper;

    @Test
    void shouldReturnWorkOrderResumeDTOList() {
        // Arrange
        WorkOrderFilterDTO filterDTO = new WorkOrderFilterDTO();
        filterDTO.setSortBy("id");
        filterDTO.setSortDirection("ASC");

        List<WorkOrderModel> mockWorkOrders = new ArrayList<>();
        WorkOrderModel mockWorkOrder = mock(WorkOrderModel.class);
        mockWorkOrders.add(mockWorkOrder);

        when(workOrderRepository.findAll(
                ArgumentMatchers.<Specification<WorkOrderModel>>any(),
                any(Sort.class)
        )).thenReturn(mockWorkOrders);

        WorkOrderResumeDTO mockResumeDTO = new WorkOrderResumeDTO();
        when(workOrderMapper.toWorkOrderResumeDTO(mockWorkOrder)).thenReturn(mockResumeDTO);

        // Act
        List<WorkOrderResumeDTO> result = useCase.execute(filterDTO).getData();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(mockResumeDTO, result.get(0));

        verify(workOrderRepository).findAll(any(Specification.class), any(Sort.class));
        verify(workOrderMapper).toWorkOrderResumeDTO(mockWorkOrder);
    }
}
