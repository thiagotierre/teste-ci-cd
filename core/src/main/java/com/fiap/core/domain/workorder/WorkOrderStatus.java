package com.fiap.core.domain.workorder;

public enum WorkOrderStatus {
    RECEIVED("Recebido"),
    IN_DIAGNOSIS("Em diagnóstico"),
    AWAITING_APPROVAL("Aguardando aprovação"),
    REFUSED("Recusado"),
    IN_PROGRESS("Em andamento"),
    COMPLETED("Finalizado"),
    DELIVERED("Entregue");

    private final String description;

    WorkOrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static WorkOrderStatus fromString(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        try {
            return WorkOrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}