package com.mosinsa.product.controller.request;

import com.mosinsa.product.entity.DiscountPolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {

    private String name;

    private int price;

    private int stock;

    private DiscountPolicy discountPolicy;

    private long likes;



}
