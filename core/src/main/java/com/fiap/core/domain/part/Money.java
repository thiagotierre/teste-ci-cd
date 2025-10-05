package com.fiap.core.domain.part;

import com.fiap.core.exception.BusinessRuleException;
import java.math.BigDecimal;
import java.util.Objects;

public final class Money {

    private final BigDecimal value;

    private Money(BigDecimal value) {
        this.value = value;
    }

    public static Money of(BigDecimal value) throws BusinessRuleException {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessRuleException("Price value cannot be null or negative.", "MONEY-001");
        }
        return new Money(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(value, money.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}