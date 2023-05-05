package com.shopping.mosinsa.controller.request;

import com.shopping.mosinsa.entity.DiscountPolicy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor
public class ProductAddRequest {

    private String name;
    private int price;
    private int stock;
    private DiscountPolicy discountPolicy;

    public ProductAddRequest(String name, int price, int stock, DiscountPolicy discountPolicy) {
        Assert.hasText(name, "상품명은 필수입니다.");
        Assert.isTrue(price > 0, "상품 가격은 0보다 커야합니다.");
        Assert.isTrue(stock > 0, "상품 수량은 0보다 커야합니다.");
        Assert.hasText(name, "할인 정책은 필수입니다.");

        this.name = name;
        this.price = price;
        this.stock = stock;
        this.discountPolicy = discountPolicy;
    }

}
