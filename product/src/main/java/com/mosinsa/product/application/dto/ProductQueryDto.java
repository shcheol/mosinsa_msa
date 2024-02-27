package com.mosinsa.product.application.dto;

import com.mosinsa.product.domain.product.Product;
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
}
