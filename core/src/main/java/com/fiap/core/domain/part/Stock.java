package com.fiap.core.domain.part;

import com.fiap.core.exception.BusinessRuleException;
import lombok.Getter;

@Getter
public class Stock {

    private int stockQuantity;
    private int reservedStock;
    private final int minimumStock;

    private Stock(int stockQuantity, int reservedStock, int minimumStock) {
        this.stockQuantity = stockQuantity;
        this.reservedStock = reservedStock;
        this.minimumStock = minimumStock;
    }

    public static Stock of(int stockQuantity, int reservedStock, int minimumStock) throws BusinessRuleException {
        if (stockQuantity < 0 || reservedStock < 0 || minimumStock < 0) {
            throw new BusinessRuleException("Stock values cannot be negative.", "STOCK-001");
        }
        return new Stock(stockQuantity, reservedStock, minimumStock);
    }

    public void subtract(int quantity) throws BusinessRuleException {
        if (quantity <= 0) {
            throw new BusinessRuleException("Quantity to subtract must be positive.", "STOCK-002");
        }
        if (this.stockQuantity < quantity) {
            throw new BusinessRuleException("Insufficient stock.", "STOCK-003");
        }
        this.stockQuantity -= quantity;
        this.reservedStock += quantity;
    }

    public void restore(int quantity) throws BusinessRuleException {
        if (quantity <= 0) {
            throw new BusinessRuleException("Quantity to restore must be positive.", "STOCK-004");
        }
        if (this.reservedStock < quantity) {
            throw new BusinessRuleException("Cannot restore more than what is reserved.", "STOCK-005");
        }
        this.stockQuantity += quantity;
        this.reservedStock -= quantity;
    }
}