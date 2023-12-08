package com.mosinsa.order.ui.response;

import com.mosinsa.order.db.DiscountPolicy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
