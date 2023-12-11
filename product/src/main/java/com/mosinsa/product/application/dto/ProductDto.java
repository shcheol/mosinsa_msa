package com.mosinsa.product.application.dto;

import com.mosinsa.product.domain.product.Product;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductDto {

    private String productId;
    private String name;

    private int price;

    private long stock;

    private int discountPrice;

    private long likes;

    public ProductDto(Product product) {
        this.productId = product.getId().getId();
        this.name = product.getName();
        this.price = product.getPrice().getPrice();
        this.stock = product.getStock().getRemain();
        this.likes = product.getLikes().size();

    }
}
