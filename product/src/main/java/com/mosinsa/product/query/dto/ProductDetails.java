package com.mosinsa.product.query.dto;

import com.mosinsa.product.command.domain.Product;
import lombok.Value;

import java.util.List;

@Value
public class ProductDetails {
    String productId;
    String name;
    int price;
    SalesDto sales;
	BrandDto brand;
	List<ProductOptionDto> productOptions;
	OptionCombinationMapDto optionCombinationMap;

    public ProductDetails(Product product, List<ProductOptionDto> productOptionDtos, OptionCombinationMapDto optionCombinationMap, SalesDto sales) {
        this.productId = product.getId().getId();
        this.name = product.getName();
        this.price = product.getPrice().getValue();
        this.sales = sales;
		this.brand = new BrandDto(product.getBrand().getId(), product.getBrand().getName());
		this.productOptions = productOptionDtos;
		this.optionCombinationMap = optionCombinationMap;
    }
}
