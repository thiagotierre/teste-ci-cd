package com.fiap.challenge.workOrders.entity.enums;

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
        try {
            return WorkOrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}