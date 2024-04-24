package com.mosinsa.order.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class OrderProduct {
	private String productId;

	@Convert(converter = MoneyConverter.class)
	@Column(name = "price")
	private Money price;

	private int quantity;

	@Convert(converter = MoneyConverter.class)
	@Column(name = "amounts")
	private Money amounts;

	public static OrderProduct create(String productId, int price, int quantity) {

		OrderProduct orderProduct = new OrderProduct();
		orderProduct.productId = productId;
		orderProduct.price = Money.of(price);
		orderProduct.setQuantity(quantity);
		orderProduct.amounts = orderProduct.price.multiply(quantity);

		return orderProduct;
	}

	private void setQuantity(int quantity) {
		if (quantity < 1) {
			throw new IllegalArgumentException();
		}

		this.quantity = quantity;
	}

}
