package com.mosinsa.order.dto;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Value
public class CreateOrderDto {

	String customerId;
	String couponId;
	String discountPolicy;
	boolean available;
	List<MyOrderProduct> myOrderProducts = new ArrayList<>();

	public CreateOrderDto(String customerId, String couponId, String discountPolicy, boolean available, List<com.mosinsa.order.ui.request.CreateOrderRequest.MyOrderProduct> myOrderProducts) {
		this.customerId = customerId;
		this.couponId = couponId;
		this.discountPolicy = discountPolicy;
		this.available = available;
		myOrderProducts.forEach(op ->
			this.myOrderProducts.add(new MyOrderProduct(op.getProductId(), op.getPrice(), op.getQuantity())));
		valid();
	}

	public void valid() {
		if (!StringUtils.hasText(customerId)) {
			throw new OrderException(OrderError.VALIDATION_ERROR);
		}
		if (myOrderProducts.isEmpty()) {
			throw new OrderException(OrderError.VALIDATION_ERROR);
		}
	}

	@AllArgsConstructor
	@Getter
	public static class MyOrderProduct {
		String productId;
		Integer price;
		Integer quantity;
	}
}
