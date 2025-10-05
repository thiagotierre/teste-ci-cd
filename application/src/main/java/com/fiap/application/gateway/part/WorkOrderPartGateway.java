package com.fiap.application.gateway.part;

import java.util.UUID;

public interface WorkOrderPartGateway {
    boolean existsByPartId(UUID partId);
}