package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.Money;
import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.command.domain.Sales;
import com.mosinsa.product.query.dto.SalesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SalesService {

    private final SalesPolicyStrategyProvider salesPolicyProvider;
    private final DiscountStrategyProvider discountStrategyProvider;

    public SalesDto calculate(Product product) {
        List<Sales> sales = product.getSales();
        Money price = product.getPrice();
        return sales.stream()
                .map(sale -> discountStrategyProvider
                        .provide(sale.getDiscount().getDiscountType())
                        .calculate(price, sale))
                .max(Comparator.comparingInt(SalesDto::discountRate))
                .orElseThrow();
    }
}
