package com.fiap.challenge.vehicles.useCases.find.byClientCpf;

import java.util.List;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;

public interface FindVehiclesByCustomerDocumentUseCase {

	ResponseApi<List<VehicleResponseDTO>> execute(String cpfCnpj);
}
