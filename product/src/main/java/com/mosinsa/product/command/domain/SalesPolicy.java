package com.mosinsa.product.command.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
public class SalesPolicy {

    @Enumerated(EnumType.STRING)
    private SalesPolicyType salesPolicyType;
    private String startCondition;
    private String endCondition;

    public static SalesPolicy of(SalesPolicyType salesPolicyType, String startCondition, String endCondition) {
        SalesPolicy salesPolicy = new SalesPolicy();
        salesPolicy.salesPolicyType = salesPolicyType;
        salesPolicy.startCondition = startCondition;
        salesPolicy.endCondition = endCondition;
        return salesPolicy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalesPolicy that = (SalesPolicy) o;

        if (salesPolicyType != that.salesPolicyType) return false;
        if (!Objects.equals(startCondition, that.startCondition))
            return false;
        return Objects.equals(endCondition, that.endCondition);
    }

    @Override
    public int hashCode() {
        int result = salesPolicyType != null ? salesPolicyType.hashCode() : 0;
        result = 31 * result + (startCondition != null ? startCondition.hashCode() : 0);
        result = 31 * result + (endCondition != null ? endCondition.hashCode() : 0);
        return result;
    }
}
