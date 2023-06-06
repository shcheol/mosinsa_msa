package com.mosinsa.order.dto;

import com.mosinsa.order.entity.DiscountPolicy;
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

    public ProductDto(Long id, String name, int price, int stock, DiscountPolicy discountPolicy, int discountPrice, long likes) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.discountPolicy = discountPolicy;
        this.discountPrice = discountPrice;
        this.likes = likes;
    }

    //    public ProductDto(Product product) {
//        this.id = product.getId();
//        this.name = product.getName();
//        this.price = product.getPrice();
//        this.stock = product.getStock();
//        this.discountPolicy = product.getDiscountPolicy();
//        this.discountPrice = product.getDiscountPrice();
//        this.likes = product.getLikes();
//    }
}
