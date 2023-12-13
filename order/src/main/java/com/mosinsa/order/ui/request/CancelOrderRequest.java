package com.mosinsa.order.ui.request;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor
public class CancelOrderRequest {

    private String customerId;
    private String orderId;

    public CancelOrderRequest(String customerId, String orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
		valid();
    }

	private void valid(){
		if(!StringUtils.hasText(customerId)){
			throw new OrderException(OrderError.VALIDATION_ERROR);
		}
		if(!StringUtils.hasText(orderId)){
			throw new OrderException(OrderError.VALIDATION_ERROR);
		}
	}
}
