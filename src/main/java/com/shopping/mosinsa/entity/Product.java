package com.shopping.mosinsa.entity;

import com.shopping.mosinsa.controller.request.ProductAddRequest;
import com.shopping.mosinsa.controller.request.ProductUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private int price;

    private int stock;

    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;

    private long likes;

    public Product(String name, int price, int stock, DiscountPolicy discountPolicy) {

        Assert.hasText(name, "상품명은 필수입니다.");
        Assert.isTrue(price > 0, "상품 가격은 0보다 커야합니다.");
        Assert.isTrue(stock > 0, "상품 재고는 0보다 커야합니다.");
        Assert.hasText(discountPolicy.name(), "할인 정책은 필수입니다.");

        this.name = name;
        this.price = price;
        this.stock = stock;
        this.discountPolicy = discountPolicy;
        this.likes = 0;
    }

    public Product(ProductAddRequest request) {

        Assert.hasText(request.getName(), "상품명은 필수입니다.");
        Assert.isTrue(request.getPrice() > 0, "상품 가격은 0보다 커야합니다.");
        Assert.isTrue(request.getStock() > 0, "상품 재고는 0보다 커야합니다.");
        Assert.hasText(request.getDiscountPolicy().name(), "할인 정책은 필수입니다.");

        this.name = request.getName();
        this.price = request.getPrice();
        this.stock = request.getStock();
        this.discountPolicy = request.getDiscountPolicy();
        this.likes = 0;
    }

    public void change(ProductUpdateRequest request) {

        Assert.hasText(request.getName(), "상품명은 필수입니다.");
        Assert.isTrue(request.getPrice() > 0, "상품 가격은 0보다 커야합니다.");
        Assert.isTrue(request.getStock() > 0, "상품 재고는 0보다 커야합니다.");
        Assert.hasText(request.getDiscountPolicy().name(), "할인 정책은 필수입니다.");
        Assert.isTrue(request.getLikes() >= 0, "좋아요 수는 0보다 작을 수 없습니다.");


        this.name = request.getName();
        this.price = request.getPrice();
        this.stock = request.getStock();
        this.discountPolicy = request.getDiscountPolicy();
        this.likes = request.getLikes();
    }

    public void addStock(int stock) {
        Assert.isTrue(stock > 0, "수량은 양수 값이어야 합니다.");
        this.stock += stock;
    }

    public void removeStock(int stock) {
        Assert.isTrue(stock > 0, "수량은 양수 값이어야 합니다.");
        int remain = this.stock - stock;
        if(remain < 0){
            throw new IllegalArgumentException("수량이 부족합니다.");
        }

        this.stock = this.stock - stock;
    }

    public int getDiscountPrice() {
        return this.discountPolicy.applyDiscountPrice(this.price);
    }

}
