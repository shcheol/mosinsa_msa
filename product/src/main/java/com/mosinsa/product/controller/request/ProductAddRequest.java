package com.mosinsa.product.controller.request;

import com.mosinsa.product.db.entity.DiscountPolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductAddRequest {

    private String name;
    private int price;
    private int stock;
    private DiscountPolicy discountPolicy = DiscountPolicy.NONE;

}
