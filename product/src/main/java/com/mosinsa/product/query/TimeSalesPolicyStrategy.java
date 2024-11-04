package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.Sales;
import com.mosinsa.product.command.domain.SalesPolicyType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TimeSalesPolicyStrategy implements SalesPolicyStrategy {

	private final Clock clock;
	@Override
	public boolean meetCondition(Sales sale) {

		LocalDate now = LocalDate.now(clock);

		LocalDate start = LocalDate.parse(sale.getSalesPolicy().getStartCondition());
		LocalDate end= LocalDate.parse(sale.getSalesPolicy().getEndCondition());

		boolean after = now.isAfter(start) || now.isEqual(start);
		boolean before = now.isBefore(end) || now.isEqual(end);

		return after && before && sale.isActive();
	}

	@Override
	public boolean isSupport(SalesPolicyType salesPolicyType) {
		return SalesPolicyType.TIME_SALE.equals(salesPolicyType);
	}
}
