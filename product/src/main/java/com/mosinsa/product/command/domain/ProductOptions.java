package com.mosinsa.product.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class ProductOptions extends BaseIdEntity{

	@Enumerated(EnumType.STRING)
	private ProductOption optionName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productOptions", cascade = CascadeType.ALL)
	private List<ProductOptionsValue> productOptionsValues = new ArrayList<>();

	public static ProductOptions of(ProductOption productOptions){
		ProductOptions productOption = new ProductOptions();
		productOption.optionName = productOptions;
		return productOption;
	}

	protected void setProduct(Product product){
		this.product = product;
	}
	public void addOptionsValue(List<ProductOptionsValue> productOptionsValues){
		for (ProductOptionsValue productOptionsValue : productOptionsValues) {
			productOptionsValue.setProductOptions(this);
			this.productOptionsValues.add(productOptionsValue);
		}
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
