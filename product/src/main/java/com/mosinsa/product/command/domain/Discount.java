package com.mosinsa.product.command.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class Discount {

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private int amount;

    public static Discount of(DiscountType discountType, int amount){
        Discount discount = new Discount();
        discount.discountType = discountType;
        discount.amount = amount;
        return discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Discount discount = (Discount) o;

        if (amount != discount.amount) return false;
        return discountType == discount.discountType;
    }

    @Override
    public int hashCode() {
        int result = discountType != null ? discountType.hashCode() : 0;
        result = 31 * result + amount;
        return result;
    }
}
