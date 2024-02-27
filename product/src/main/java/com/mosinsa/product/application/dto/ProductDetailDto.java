package com.mosinsa.product.application.dto;

import com.mosinsa.product.domain.product.Product;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Value
public class ProductDetailDto {
    String productId;
    String name;

    int price;
    long stock;
    long likes;

	@QueryProjection
    public ProductDetailDto(Product product) {
        this.productId = product.getId().getId();
        this.name = product.getName();
        this.price = product.getPrice().getValue();
        this.stock = product.getStock().getRemain();
        this.likes = product.getLikes().getTotal();
    }
}
