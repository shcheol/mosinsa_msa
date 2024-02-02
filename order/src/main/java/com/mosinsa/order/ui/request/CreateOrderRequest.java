package com.mosinsa.order.ui.request;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Value
public class CreateOrderRequest {

    String customerId;
	CouponInfo couponInfo;
	List<MyOrderProduct> myOrderProducts = new ArrayList<>();

    public CreateOrderRequest(String customerId, CouponInfo couponInfo, List<MyOrderProduct> myOrderProducts) {
        this.customerId = customerId;
		this.couponInfo = Optional.ofNullable(couponInfo).orElse(new CouponInfo("","",""));
        this.myOrderProducts.addAll(myOrderProducts);
		valid();
    }

	public void valid(){
		if (!StringUtils.hasText(customerId)){
			throw new OrderException(OrderError.VALIDATION_ERROR);
		}
		if (myOrderProducts.isEmpty()){
			throw new OrderException(OrderError.VALIDATION_ERROR);
		}
	}

	@AllArgsConstructor
	@Getter
	public static class CouponInfo{
		String couponId;
		String discountPolicy;
		String state;
	}

	@AllArgsConstructor
	@Getter
	public static class MyOrderProduct{
		String productId;
		Integer price;
		Integer quantity;
	}
}
