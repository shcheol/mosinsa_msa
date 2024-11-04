package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.Discount;
import com.mosinsa.product.command.domain.DiscountType;
import com.mosinsa.product.command.domain.Money;
import com.mosinsa.product.command.domain.Sales;
import com.mosinsa.product.query.dto.SalesDto;
import org.springframework.stereotype.Component;

@Component
public class FixedDiscountStrategy implements DiscountStrategy{

    @Override
    public SalesDto calculate(Money price, Sales sale) {
        Discount discount = sale.getDiscount();
        int amount = discount.getAmount();
        Money minus = price.minus(amount);
        int i = 100 - (minus.getValue() *100) / price.getValue();
        return new SalesDto(minus.getValue(), i);
    }

    @Override
    public boolean isSupport(DiscountType discountType) {
        return DiscountType.FIXED.equals(discountType);
    }
}
