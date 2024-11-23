package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.Discount;
import com.mosinsa.product.command.domain.DiscountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscountStrategyProvider {
    private final List<DiscountStrategy> discountStrategyList;

    public DiscountStrategy provide(DiscountType discountType) {
        return discountStrategyList.stream()
                .filter(discountStrategy -> discountStrategy.isSupport(discountType))
                .findAny()
                .orElseThrow();
    }

}
