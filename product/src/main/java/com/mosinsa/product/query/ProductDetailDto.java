package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.Product;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Value
public class ProductDetailDto {
    String productId;
    String name;
    int price;
    long stock;

	@QueryProjection
    public ProductDetailDto(Product product) {
        this.productId = product.getId().getId();
        this.name = product.getName();
        this.price = product.getPrice().getValue();
        this.stock = product.getStock().getRemain();
    }
}
