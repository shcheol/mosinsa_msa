package com.mosinsa.product.dto;

import com.mosinsa.product.domain.Product;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductDto {

    private String productId;

    private String name;

    private int price;

    private int stock;

    private int discountPrice;

    private long likes;

    public ProductDto(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.likes = product.getLikes();
    }
}
