package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.Sales;
import com.mosinsa.product.command.domain.SalesPolicyType;
import org.springframework.stereotype.Component;

@Component
public class FirstOrderSalesPolicyStrategy implements SalesPolicyStrategy {
	@Override
	public boolean meetCondition(Sales sale) {
		return sale.isActive();
	}

	@Override
	public boolean isSupport(SalesPolicyType salesPolicyType) {
		return SalesPolicyType.FIRST_ORDER_SALE.equals(salesPolicyType);
	}
}
