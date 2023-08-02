package com.mosinsa.product.db.dto;

import com.mosinsa.product.db.entity.DiscountPolicy;
import com.mosinsa.product.db.entity.Product;
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

    private DiscountPolicy discountPolicy;

    private int discountPrice;

    private long likes;

    public ProductDto(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.discountPolicy = product.getDiscountPolicy();
        this.discountPrice = product.getDiscountPrice();
        this.likes = product.getLikes();
    }
}
