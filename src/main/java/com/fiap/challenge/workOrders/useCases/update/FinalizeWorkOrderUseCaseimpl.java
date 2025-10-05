package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.shared.model.ResponseApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FinalizeWorkOrderUseCaseimpl implements FinalizeWorkOrderUseCase {

    private final UpdateStatusWorkOrderUseCase updateStatusWorkOrderUseCase;
    private final AvarageTimeWorkOrderUseCase avarageTimeWorkOrderUseCase;

    @Override
    @Transactional
    public ResponseApi<Void> execute(UUID id) {
        ResponseApi<Void> responseApi = new ResponseApi<>();
        updateStatusWorkOrderUseCase.execute(id, "COMPLETED");
        avarageTimeWorkOrderUseCase.executeEnd(id);

        return responseApi.of(HttpStatus.OK,"Ordem de serviço finalizada com sucesso!");

    }
}
