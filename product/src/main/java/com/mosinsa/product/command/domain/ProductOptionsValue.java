package com.mosinsa.product.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ProductOptionsValue extends BaseIdEntity{

	private String optionsValue;

	@Convert(converter = MoneyConverter.class)
	@Column(name = "change_price")
	private Money changePrice;

	@Enumerated(EnumType.STRING)
	private ChangeType changeType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_option_id")
	private ProductOptions productOptions;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "stock_id")
	private Stock stock;

	public static ProductOptionsValue of(String optionsValue, Money changePrice, ChangeType changeType){
		ProductOptionsValue productOptionsValue = new ProductOptionsValue();
		productOptionsValue.optionsValue = optionsValue;
		productOptionsValue.changePrice = changePrice;
		productOptionsValue.changeType = changeType;
		return productOptionsValue;
	}

	public void addStock(Stock stock){
		this.stock =stock;
	}

	protected void setProductOptions(ProductOptions productOptions){
		this.productOptions = productOptions;
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
