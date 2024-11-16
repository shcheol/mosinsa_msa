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
		String message = """
				{
					"id":"id",
					"customerInfo":{"id":"customerId1","name":"name"},
					"orderProducts":[
						{
							"id":"9b097516","name":"SA 비건 레더 스퀘어 토트 백_블랙","quantity":1,"perPrice":48000,"totalPrice":48000,
							"options":[
								{"id":1923,"name":"YELLOW"},
								{"id":1929,"name":"M"}
							],
							"coupons":[
								{
									"id":"couponId1",
									"discountPolicy":"PERCENT_10"
								}
							]
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
		String message = """
				{
					"id":"id",
					"customerInfo":{"id":"customerId1","name":"name"},
					"orderProducts":[
						{
							"id":"9b097516","name":"SA 비건 레더 스퀘어 토트 백_블랙","quantity":1,"perPrice":48000,"totalPrice":48000,
							"options":[
								{"id":1923,"name":"YELLOW"},
								{"id":1929,"name":"M"}
							],
							"coupons":[
							]
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