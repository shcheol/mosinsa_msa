package com.mosinsa.product.query.dto;

import com.mosinsa.product.command.domain.Product;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

@Value
public class ProductSummary {
	String productId;
	String name;
	int price;

	@QueryProjection
	public ProductSummary(Product product) {
		this.productId = product.getId().getId();
		this.name = product.getName();
		this.price = product.getPrice().getValue();
	}

	public ProductSummary(String productId, String name, int price) {
		this.productId = productId;
		this.name = name;
		this.price = price;
	}
}
