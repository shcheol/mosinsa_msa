package com.mosinsa.product.query.dto;

import com.mosinsa.product.command.domain.ChangeType;
import com.mosinsa.product.command.domain.ProductOptionsValue;

public record ProductOptionsValueDto(String optionValue, int changePrice, ChangeType changeType) {

	public static ProductOptionsValueDto of(ProductOptionsValue productOptionsValue) {
		return new ProductOptionsValueDto(productOptionsValue.getOptionsValue(),
				productOptionsValue.getChangePrice().getValue(),
				productOptionsValue.getChangeType());
	}
}
