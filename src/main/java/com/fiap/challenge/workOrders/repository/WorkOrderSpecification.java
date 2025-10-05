package com.fiap.challenge.workOrders.repository;

import com.fiap.challenge.workOrders.dto.WorkOrderFilterDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class WorkOrderSpecification {

    public static Specification<WorkOrderModel> list(WorkOrderFilterDTO filterDTO) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterDTO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filterDTO.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
