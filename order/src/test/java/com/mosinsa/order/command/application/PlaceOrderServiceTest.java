package com.mosinsa.order.command.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PlaceOrderServiceTest {
	ObjectMapper om;
	OrderConfirmDto orderWithCoupon;
	OrderConfirmDto orderWithoutCoupon;
	@Autowired
	PlaceOrderService placeOrderService;

	@Test
	void orderWithCoupon() {
		Order order = placeOrderService.order(OrderId.newId(), orderWithCoupon);
		assertThat(order.getOrderProducts()).hasSize(1);
		assertThat(order.getCustomerId()).isEqualTo("customerId");
		assertThat(order.getOrderCoupon().getCouponId()).isEqualTo("couponId");
		assertThat(order.getTotalPrice().getValue()).isEqualTo(5400);
	}

	@Test
	void orderWithoutCoupon() {
		Order order = placeOrderService.order(OrderId.newId(), orderWithoutCoupon);
		assertThat(order.getOrderProducts()).hasSize(1);
		assertThat(order.getCustomerId()).isEqualTo("customerId");
		assertThat(order.getOrderCoupon().getCouponId()).isEmpty();
		assertThat(order.getTotalPrice().getValue()).isEqualTo(6000);
	}

	@BeforeEach
	void init() throws JsonProcessingException {
		om = new ObjectMapper();
		String s = """
				{
								         "couponId": "couponId",
								         "customerId": "customerId",
								         "orderProducts": [
								             {
								                 "productId": "productId",
								                 "price": 3000,
								                 "quantity": 2,
								                 "amounts": 6000
								             }
								         ],
								         "shippingInfo": {
								             "message": "home",
								             "address": {
								                 "zipCode": "zipcode",
								                 "address1": "address1",
								                 "address2": "address2"
								             },
								             "receiver": {
								                 "name": "myname",
								                 "phoneNumber": "010-1111-1111"
								             }
								         },
								         "totalAmount": 5400
								     }
				 				 
								""";
		orderWithCoupon = om.readValue(s, OrderConfirmDto.class);

		String noCoupon = """
								
				{
				         "customerId": "customerId",
				         "orderProducts": [
				             {
				                 "productId": "productId",
				                 "price": 3000,
				                 "quantity": 2,
				                 "amounts": 6000
				             }
				         ],
				         "shippingInfo": {
				             "message": "home",
				             "address": {
				                 "zipCode": "zipcode",
				                 "address1": "address1",
				                 "address2": "address2"
				             },
				             "receiver": {
				                 "name": "myname",
				                 "phoneNumber": "010-1111-1111"
				             }
				         },
				         "totalAmount": 6000
				     }
				 
				""";
		orderWithoutCoupon = om.readValue(noCoupon, OrderConfirmDto.class);
	}

}