package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.Discount;
import com.mosinsa.product.command.domain.DiscountType;
import com.mosinsa.product.command.domain.Money;
import com.mosinsa.product.command.domain.Sales;
import com.mosinsa.product.query.dto.SalesDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RateDiscountStrategyTest {

	@Test
	void calculate() {
		RateDiscountStrategy rateDiscountStrategy = new RateDiscountStrategy();
		SalesDto calculate = rateDiscountStrategy.calculate(Money.of(1000), Sales.of(null, Discount.of(DiscountType.RATE, 10)));

		assertThat(calculate.discountRate()).isEqualTo(10);
		assertThat(calculate.discountedPrice()).isEqualTo(900);

		SalesDto calculate2 = rateDiscountStrategy.calculate(Money.of(217000), Sales.of(null, Discount.of(DiscountType.RATE, 20)));
		assertThat(calculate2.discountRate()).isEqualTo(20);
		assertThat(calculate2.discountedPrice()).isEqualTo(173600);
	}
}