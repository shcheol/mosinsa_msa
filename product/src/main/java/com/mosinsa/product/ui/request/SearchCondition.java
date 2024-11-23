package com.mosinsa.product.ui.request;

import com.mosinsa.product.command.domain.SalesPolicyType;

public record SearchCondition(String categoryId, SalesPolicyType sales) {
}
