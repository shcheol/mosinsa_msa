package com.mosinsa.product.dto;

import com.mosinsa.product.entity.DiscountPolicy;
import com.mosinsa.product.entity.Product;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
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
