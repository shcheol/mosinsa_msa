package com.mosinsa.order.application.dto;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.infra.feignclient.coupon.CouponResponse;
import com.mosinsa.order.ui.request.MyOrderProduct;
import lombok.Value;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Value
public class CreateOrderDto {

	String customerId;

	CouponResponse couponResponse;
	List<MyOrderProduct> myOrderProducts = new ArrayList<>();

	public CreateOrderDto(String customerId, CouponResponse couponResponse, List<MyOrderProduct> myOrderProducts) {
		this.customerId = customerId;
		this.couponResponse = couponResponse;
		myOrderProducts.forEach(op ->
			this.myOrderProducts.add(new MyOrderProduct(op.productId(), op.price(), op.quantity())));
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

}
