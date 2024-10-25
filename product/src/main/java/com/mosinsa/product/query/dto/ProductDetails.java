package com.mosinsa.product.query.dto;

import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.command.domain.StockStatus;
import lombok.Value;

@Value
public class ProductDetails {
    String productId;
    String name;
    int price;
    long currentStock;
    long totalStock;
	StockStatus stockStatus;

    public ProductDetails(Product product, long currentStock) {
        this.productId = product.getId().getId();
        this.name = product.getName();
        this.price = product.getPrice().getValue();
        this.currentStock = currentStock;
        this.totalStock = product.getStock().getTotal();
		this.stockStatus = product.getStock().getStatus();
    }

	public ProductDetails(String productId, String name, int price, long currentStock, long totalStock, StockStatus stockStatus) {
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.currentStock = currentStock;
		this.totalStock = totalStock;
		this.stockStatus = stockStatus;
	}
}
