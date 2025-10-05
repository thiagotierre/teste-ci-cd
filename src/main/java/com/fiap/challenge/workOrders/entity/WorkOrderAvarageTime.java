package com.fiap.challenge.workOrders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avarage_time_work_order")
public class WorkOrderAvarageTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "work_order_id", nullable = false)
    private UUID workOrderId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    public void initTime(){
        this.startTime = LocalDateTime.now().withNano(0);
    }

    public void finishTime() {
        this.endTime = LocalDateTime.now().withNano(0);
    }

    public Duration avarageTime() {
        if (startTime == null || endTime == null) {
            throw new IllegalStateException("Start time or end time is not set.");
        }
        return Duration.between(startTime, endTime);
    }
}
