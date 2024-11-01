package com.mosinsa.product.query.dto;

import com.mosinsa.product.command.domain.Product;
import lombok.Value;

import java.util.List;

@Value
public class ProductDetails {
    String productId;
    String name;
    int price;
	BrandDto brand;
	List<ProductOptionDto> productOptions;
	OptionCombinationMapDto optionCombinationMap;

    public ProductDetails(Product product, List<ProductOptionDto> productOptionDtos, OptionCombinationMapDto optionCombinationMap) {
        this.productId = product.getId().getId();
        this.name = product.getName();
        this.price = product.getPrice().getValue();
		this.brand = new BrandDto(product.getBrand().getId(), product.getBrand().getName());
		this.productOptions = productOptionDtos;
		this.optionCombinationMap = optionCombinationMap;
    }
}
