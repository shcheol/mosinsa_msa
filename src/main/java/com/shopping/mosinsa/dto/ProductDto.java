package com.shopping.mosinsa.dto;

import com.shopping.mosinsa.entity.DiscountPolicy;
import com.shopping.mosinsa.entity.Product;
import lombok.Getter;

@Getter
public class ProductDto {

    private Long id;

    private String name;

    private int price;

    private int stock;

    private DiscountPolicy discountPolicy;

    private int discountPrice;

    private long likes;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.discountPolicy = product.getDiscountPolicy();
        this.discountPrice = product.getDiscountPrice();
        this.likes = product.getLikes();
    }
}
