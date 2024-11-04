package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.Discount;
import com.mosinsa.product.command.domain.DiscountType;
import com.mosinsa.product.command.domain.Money;
import com.mosinsa.product.command.domain.Sales;
import com.mosinsa.product.query.dto.SalesDto;
import org.springframework.stereotype.Component;

@Component
public class RateDiscountStrategy implements DiscountStrategy {

    @Override
    public SalesDto calculate(Money price, Sales sale) {

        Discount discount = sale.getDiscount();
		int amount = discount.getAmount();

		int i = price.getValue() - price.getValue() * amount /100;
		return new SalesDto(i, amount);
    }

    @Override
    public boolean isSupport(DiscountType discountType) {
        return DiscountType.RATE.equals(discountType);
    }
}
