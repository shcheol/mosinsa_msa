package com.mosinsa.order.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class OrderProduct extends IdBaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	private String productId;
	@Convert(converter = MoneyConverter.class)
	@Column(name = "price")
	private Money price;
	private int quantity;
	@Convert(converter = MoneyConverter.class)
	@Column(name = "amounts")
	private Money amounts;

	public static OrderProduct of(String productId, int price, int quantity) {

		OrderProduct orderProduct = new OrderProduct();
		orderProduct.productId = productId;
		orderProduct.price = Money.of(price);
		orderProduct.setQuantity(quantity);
		orderProduct.amounts = orderProduct.price.multiply(quantity);
		return orderProduct;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	private void setQuantity(int quantity) {
		if (quantity < 1) {
			throw new IllegalArgumentException();
		}
		this.quantity = quantity;
	}

	protected OrderProduct() {

	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

}
