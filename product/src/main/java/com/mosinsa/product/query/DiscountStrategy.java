package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.DiscountType;
import com.mosinsa.product.command.domain.Money;
import com.mosinsa.product.command.domain.Sales;
import com.mosinsa.product.query.dto.SalesDto;

public interface DiscountStrategy {
    SalesDto calculate(Money price, Sales sale);

    boolean isSupport(DiscountType discountType);

}
