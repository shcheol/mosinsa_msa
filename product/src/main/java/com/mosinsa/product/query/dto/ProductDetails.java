package com.mosinsa.product.query.dto;

import com.mosinsa.product.command.domain.Product;
import lombok.Value;

import java.util.List;

@Value
public class ProductDetails {
    String productId;
    String name;
    int price;
	List<ProductOptionDto> productOptions;

    public ProductDetails(Product product, List<ProductOptionDto> productOptionDtos) {
        this.productId = product.getId().getId();
        this.name = product.getName();
        this.price = product.getPrice().getValue();
		this.productOptions = productOptionDtos;
//        this.currentStock = currentStock;
//        this.totalStock = product.getStock().getTotal();
//		this.stockStatus = product.getStock().getStatus();
    }
}
