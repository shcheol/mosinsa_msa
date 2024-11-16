package com.mosinsa.order.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class OrderProduct extends BaseIdEntity {

	private String productId;

	private String name;
	@Convert(converter = MoneyConverter.class)
	@Column(name = "price")
	private Money price;
	private int quantity;
	@Convert(converter = MoneyConverter.class)
	@Column(name = "amounts")
	private Money amounts;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderProduct", cascade = CascadeType.ALL)
	private List<ProductOption> productOptions = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderProduct", cascade = CascadeType.ALL)
	private List<OrderCoupon> orderCoupons = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	public static OrderProduct of(String productId, String name, int price, int quantity, int amounts) {

		OrderProduct orderProduct = new OrderProduct();
		orderProduct.productId = productId;
		orderProduct.name = name;
		orderProduct.price = Money.of(price);
		orderProduct.setQuantity(quantity);
		orderProduct.amounts = Money.of(amounts);
		return orderProduct;
	}

	public void addProductOptions(List<ProductOption> productOptions) {
		for (ProductOption productOption : productOptions) {
			this.productOptions.add(productOption);
			productOption.setOrderProduct(this);
		}
	}

	protected void setOrder(Order order) {
		this.order = order;
	}

	private void setQuantity(int quantity) {
		if (quantity < 1) {
			throw new IllegalArgumentException();
		}
		this.quantity = quantity;
	}

	public List<OrderCoupon> getOrderCoupon() {
		return orderCoupons;
	}

	public void addCoupons(List<OrderCoupon> orderCoupons) {
		for (OrderCoupon orderCoupon : orderCoupons) {
			this.orderCoupons.add(orderCoupon);
			orderCoupon.setOrderProduct(this);
		}
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
