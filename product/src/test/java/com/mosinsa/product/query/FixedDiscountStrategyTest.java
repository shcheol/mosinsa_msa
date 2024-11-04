package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.*;
import com.mosinsa.product.query.dto.SalesDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FixedDiscountStrategyTest {

	@Test
	void calculate() {
		FixedDiscountStrategy fixedDiscountStrategy = new FixedDiscountStrategy();
		SalesDto calculate = fixedDiscountStrategy.calculate(Money.of(1000), Sales.of(null, Discount.of(DiscountType.FIXED, 100)));

		assertThat(calculate.discountRate()).isEqualTo(10);
		assertThat(calculate.discountedPrice()).isEqualTo(900);

		SalesDto calculate2 = fixedDiscountStrategy.calculate(Money.of(217000), Sales.of(null, Discount.of(DiscountType.FIXED, 2000)));
		assertThat(calculate2.discountRate()).isEqualTo(1);
		assertThat(calculate2.discountedPrice()).isEqualTo(215000);
	}
}