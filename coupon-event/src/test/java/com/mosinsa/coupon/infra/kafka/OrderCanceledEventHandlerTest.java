package com.mosinsa.coupon.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.coupon.ui.CouponServiceStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderCanceledEventHandlerTest {

	@Test
	@DisplayName("쿠폰사용한 주문 취소 이벤트")
	void orderCanceledEvent() throws JsonProcessingException {
		String message= """
				{
					"orderId": "orderId",
					"customerId": "customer1",
					"couponId": "couponId1",
					"orderProducts": [
						{
							"productId": "productId1",
							"price": 1000,
							"quantity": 10,
							"amounts": 10000
						}
					]
				}
				""";

		CouponServiceStub couponService = new CouponServiceStub();
		assertThat(couponService.getCancelCalledCount()).isZero();
		OrderCanceledEventHandler orderCanceledEventHandler = new OrderCanceledEventHandler(couponService, new ObjectMapper());
		orderCanceledEventHandler.orderCanceledEvent(message);
		assertThat(couponService.getCancelCalledCount()).isEqualTo(1);
	}

	@Test
	@DisplayName("쿠폰없는 주문 취소 이벤트")
	void noCouponOrderCanceledEvent() throws JsonProcessingException {
		String message= """
				{
					"orderId": "orderId",
					"customerId": "customer1",
					"orderProducts": [
						{
							"productId": "productId1",
							"price": 1000,
							"quantity": 10,
							"amounts": 10000
						}
					]
				}
				""";

		CouponServiceStub couponService = new CouponServiceStub();
		assertThat(couponService.getCancelCalledCount()).isZero();
		OrderCanceledEventHandler orderCanceledEventHandler = new OrderCanceledEventHandler(couponService, new ObjectMapper());
		orderCanceledEventHandler.orderCanceledEvent(message);
		assertThat(couponService.getCancelCalledCount()).isZero();

	}

}