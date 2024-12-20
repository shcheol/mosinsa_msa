package com.mosinsa.product.query.dto;


import com.mosinsa.product.command.domain.Product;

public record ProductSummary(String productId, String name, int price, BrandDto brand, SalesDto sales) {

	public static ProductSummary of(Product product, SalesDto sales) {
		return new ProductSummary(product.getId().getId(),
				product.getName(),
				product.getPrice().getValue(),
				new BrandDto(product.getBrand().getId(), product.getBrand().getName()),
				sales);
	}
}
