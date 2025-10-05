package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.dto.WorkOrderFilterDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderResumeDTO;
import com.fiap.challenge.workOrders.mapper.WorkOrderMapper;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import com.fiap.challenge.workOrders.repository.WorkOrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindWorkOrdersByFilterUseCaseImpl implements FindWorkOrdersByFilterUseCase {

    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderMapper workOrderMapper;

    @Override
    @Transactional
    public ResponseApi<List<WorkOrderResumeDTO>> execute(WorkOrderFilterDTO filterDTO) {
        ResponseApi<List<WorkOrderResumeDTO>> responseApi = new ResponseApi<>();
        Sort sort = Sort.by(Sort.Direction.fromString(filterDTO.getSortDirection()), filterDTO.getSortBy());

        var workOrders = workOrderRepository.findAll(WorkOrderSpecification.list(filterDTO), sort);

        List<WorkOrderResumeDTO> listWorkOrders = new ArrayList<>();

        for (var workOrder : workOrders) {
            var workOrderResume = workOrderMapper.toWorkOrderResumeDTO(workOrder);
            listWorkOrders.add(workOrderResume);
        }

        return responseApi.of(HttpStatus.OK, "Ordens de serviço encontradas com sucesso!", listWorkOrders);
    }
}
