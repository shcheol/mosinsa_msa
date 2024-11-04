package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.Sales;
import com.mosinsa.product.command.domain.SalesPolicyType;

public interface SalesPolicyStrategy {

	boolean meetCondition(Sales sale);

	boolean isSupport(SalesPolicyType salesPolicyType);
}
