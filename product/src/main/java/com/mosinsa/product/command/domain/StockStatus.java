package com.mosinsa.product.command.domain;

public enum StockStatus {
	ON("활성"), OFF("비활성"), SOLD_OUT("품절")
	;

	final String description;

	StockStatus(String description) {
		this.description = description;
	}
}
