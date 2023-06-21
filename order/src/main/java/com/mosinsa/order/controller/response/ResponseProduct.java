package com.mosinsa.order.controller.response;

import com.mosinsa.order.entity.DiscountPolicy;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseProduct {

    private String productId;

    private String name;

    private int price;

    private int stock;

    private DiscountPolicy discountPolicy;

    private int discountPrice;

    private long likes;

    public ResponseProduct(String productId, String name, int price, int stock, DiscountPolicy discountPolicy, int discountPrice, long likes) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.discountPolicy = discountPolicy;
        this.discountPrice = discountPrice;
        this.likes = likes;
    }

}
