package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.SalesPolicyType;

public interface SalesPolicyStrategy {

    boolean isSupport(SalesPolicyType salesPolicyType);
}
