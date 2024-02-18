package com.mosinsa.order.ui.request;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Value
public class CreateOrderRequest {

    String customerId;
	String couponId;
	List<MyOrderProduct> myOrderProducts = new ArrayList<>();

    public CreateOrderRequest(String customerId, String couponId, List<MyOrderProduct> myOrderProducts) {
        this.customerId = customerId;
		this.couponId = couponId;
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

}
