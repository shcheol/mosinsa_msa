package com.mosinsa.product.domain;

import com.mosinsa.product.common.ex.ProductError;
import com.mosinsa.product.common.ex.ProductException;
import com.mosinsa.product.ui.request.ProductAddRequest;
import com.mosinsa.product.ui.request.ProductUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @EmbeddedId
    @Column(name = "product_id")
    private ProductId id;

    private String name;

	@Embedded
    private Money price;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Stock stock;

	@Embedded
    private Likes likes;

    public Product(String name, int price, int stock) {

        Assert.hasText(name, "상품명은 필수입니다.");
        Assert.isTrue(price > 0, "상품 가격은 0보다 커야합니다.");
        Assert.isTrue(stock > 0, "상품 재고는 0보다 커야합니다.");

        this.id = ProductId.newId();
        this.name = name;
        this.price = Money.of(price);
        this.stock = Stock.of(stock);
        this.likes = 0;
    }

    public Product(ProductAddRequest request) {

		this.id = ProductId.newId();
        this.name = request.getName();
        this.price = request.getPrice();
        this.stock = Stock.of(request.getStock());
        this.likes = 0;
    }

    public void change(ProductUpdateRequest request) {

        this.name = request.getName();
        this.price = request.getPrice();
		this.stock = Stock.of(request.getStock());
        this.likes = request.getLikes();
    }

    public void addStock(int stock) {
		if (stock <= 0){
			throw new ProductException(ProductError.VALIDATION_ERROR, "수량은 0보다 커야합니다.");
		}
        this.stock += stock;
    }

    public void removeStock(int stock) {
		if (stock <= 0){
			throw new ProductException(ProductError.VALIDATION_ERROR, "수량은 0보다 커야합니다.");
		}
        int remain = this.stock - stock;
        if(remain < 0){
            throw new ProductException(ProductError.NOT_ENOUGH_PRODUCT_STOCK);
        }

        this.stock = remain;
    }

}
