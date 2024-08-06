package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.command.domain.StockStatus;
import lombok.Value;

@Value
public class ProductDetailDto {
    String productId;
    String name;
    int price;
    long currentStock;
    long totalStock;
	StockStatus stockStatus;

    public ProductDetailDto(Product product, long currentStock) {
        this.productId = product.getId().getId();
        this.name = product.getName();
        this.price = product.getPrice().getValue();
        this.currentStock = currentStock;
        this.totalStock = product.getStock().getTotal();
		this.stockStatus = product.getStock().getStatus();
    }
}
