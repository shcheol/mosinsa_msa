package com.mosinsa.product.command.application.dto;

import com.mosinsa.product.command.domain.Product;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

@Value
public class ProductQueryDto {
	String productId;
	String name;
	int price;

	@QueryProjection
	public ProductQueryDto(Product product) {
		this.productId = product.getId().getId();
		this.name = product.getName();
		this.price = product.getPrice().getValue();
	}

	public ProductQueryDto(String productId, String name, int price) {
		this.productId = productId;
		this.name = name;
		this.price = price;
	}
}
