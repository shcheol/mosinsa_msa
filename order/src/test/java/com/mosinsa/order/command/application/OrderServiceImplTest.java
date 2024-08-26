package com.mosinsa.order.command.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.domain.AlreadyShippedException;
import com.mosinsa.order.command.domain.OrderStatus;
import com.mosinsa.order.infra.stub.ExternalApiObjectFactory;
import com.mosinsa.order.query.application.dto.OrderDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
@Import(ExternalApiObjectFactory.class)
class OrderServiceImplTest {
	ObjectMapper om;
	OrderConfirmDto orderWithCoupon;
	OrderConfirmDto orderWithoutCoupon;

	@Autowired
	OrderService orderService;

	@Test
	void orderWithCoupon() {

		OrderDetail order = orderService.order(orderWithCoupon);
		assertThat(order.getOrderProducts()).hasSize(1);
		assertThat(order.getCustomerId()).isEqualTo("customerId");
		assertThat(order.getCouponId()).isEqualTo("couponId");
		assertThat(order.getTotalPrice()).isEqualTo(5400);
	}

	@Test
	void orderWithoutCoupon() {
//
//		when(productAdaptor.orderProducts(any(),any()))
//				.thenReturn(ResponseResult.execute(() -> {
//				}));

		OrderDetail order = orderService.order(orderWithoutCoupon);
		assertThat(order.getOrderProducts()).hasSize(1);
		assertThat(order.getCustomerId()).isEqualTo("customerId");
		assertThat(order.getCouponId()).isEmpty();
		assertThat(order.getTotalPrice()).isEqualTo(6000);
	}

	@Test
	void cancelOrder() {

		String orderId = "orderId1";
		OrderDetail canceledOrder = orderService.cancelOrder(orderId);
		assertThat(canceledOrder.getOrderId()).isEqualTo(orderId);
		assertThat(canceledOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
	}

	@Test
	void cancelOrderExternalApiFail() {
		String orderId = "orderId3";
		assertThrows(AlreadyShippedException.class, () -> orderService.cancelOrder(orderId));
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