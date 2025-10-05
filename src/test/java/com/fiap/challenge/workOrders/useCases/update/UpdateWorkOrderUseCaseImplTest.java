package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import com.fiap.challenge.workOrders.useCases.update.UpdateWorkOrderUseCaseImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UpdateWorkOrderUseCaseImplTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    @InjectMocks
    private UpdateWorkOrderUseCaseImpl updateWorkOrderUseCase;

    @Test
    void shouldCallSaveOnRepository() {
        WorkOrderModel workOrder = new WorkOrderModel(); // ou um mock se preferir

        updateWorkOrderUseCase.execute(workOrder);

        verify(workOrderRepository).save(workOrder);
    }
}
