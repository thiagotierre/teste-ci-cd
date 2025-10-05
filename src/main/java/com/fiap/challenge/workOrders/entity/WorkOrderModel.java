package com.fiap.challenge.workOrders.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work_orders")
public class WorkOrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleModel vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    private UserModel createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_mechanic_id")
    private UserModel assignedMechanic;

    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<WorkOrderPartModel> workOrderPartModels;

    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<WorkOrderServiceModel> workOrderServices;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkOrderStatus status;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "finished_at")
    private OffsetDateTime finishedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public void recalculateTotal() {
        BigDecimal totalParts = BigDecimal.ZERO;
        BigDecimal totalServices = BigDecimal.ZERO;

        if (workOrderPartModels != null && !workOrderPartModels.isEmpty()) {
            totalParts = workOrderPartModels.stream()
                    .map(part -> part.getUnitPrice().multiply(BigDecimal.valueOf(part.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        if (workOrderServices != null && !workOrderServices.isEmpty()) {
            totalServices = workOrderServices.stream()
                    .map(WorkOrderServiceModel::getAppliedPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        this.totalAmount = totalParts.add(totalServices);
    }
}