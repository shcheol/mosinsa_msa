package com.mosinsa.order.dto;

import com.mosinsa.order.controller.response.ResponseProduct;
import com.mosinsa.order.entity.DiscountPolicy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ProductDto {

    private String productId;

    private String name;

    private int price;

    private int stock;

    private DiscountPolicy discountPolicy;

    private int discountPrice;

    private long likes;

    public ProductDto(ResponseProduct product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.discountPolicy = product.getDiscountPolicy();
        this.discountPrice = product.getDiscountPrice();
        this.likes = product.getLikes();
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
