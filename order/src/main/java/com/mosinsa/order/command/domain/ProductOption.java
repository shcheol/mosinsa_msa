package com.mosinsa.order.command.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class ProductOption extends BaseIdEntity{

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_product_id")
	private OrderProduct orderProduct;

	private Long optionId;

	private String name;

	public static ProductOption of(long optionId, String name){
		ProductOption productOption = new ProductOption();
		productOption.optionId = optionId;
		productOption.name = name;
		return productOption;
	}

	protected void setOrderProduct(OrderProduct orderProduct) {
		this.orderProduct = orderProduct;
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
