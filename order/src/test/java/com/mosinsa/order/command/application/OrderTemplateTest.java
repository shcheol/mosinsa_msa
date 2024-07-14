package com.mosinsa.order.command.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.infra.api.feignclient.product.ProductFeignAdapter;
import com.mosinsa.order.infra.api.ResponseResult;
import com.mosinsa.order.infra.api.feignclient.coupon.CouponFeignAdapter;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderTemplateTest {

	@MockBean
	CouponFeignAdapter couponAdapter;

	@MockBean
	ProductFeignAdapter productAdaptor;

	@Autowired
	PlaceOrderService placeOrderService;

	ObjectMapper om;
	@Autowired
	OrderTemplate orderTemplate;
	CreateOrderRequest orderWithCoupon;
	CreateOrderRequest orderWithoutCoupon;

	Map<String, Collection<String>> header = Map.of();

	@Test
	void orderWithCoupon() {

		when(productAdaptor.orderProducts(any(), any(), any()))
				.thenReturn(ResponseResult.execute(() -> {
				}));
		when(couponAdapter.useCoupon(any(), any()))
				.thenReturn(ResponseResult.execute(() -> {
				}));

		OrderDetail order = orderTemplate.order(header, orderWithCoupon);
		assertThat(order.getOrderProducts()).hasSize(1);
		assertThat(order.getCustomerId()).isEqualTo("customerId");
		assertThat(order.getCouponId()).isEqualTo("couponId");
		assertThat(order.getTotalPrice()).isEqualTo(5400);
	}

	@Test
	void orderWithoutCoupon() {

		when(productAdaptor.orderProducts(any(),any(), any()))
				.thenReturn(ResponseResult.execute(() -> {
				}));

		OrderDetail order = orderTemplate.order(header, orderWithoutCoupon);
		assertThat(order.getOrderProducts()).hasSize(1);
		assertThat(order.getCustomerId()).isEqualTo("customerId");
		assertThat(order.getCouponId()).isNull();
		assertThat(order.getTotalPrice()).isEqualTo(6000);
	}


	@BeforeEach
	void init() throws JsonProcessingException {
		om = new ObjectMapper();
		String s = """
				{
				     "orderConfirm":{
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
				 }
				""";
		orderWithCoupon = om.readValue(s, CreateOrderRequest.class);

		String noCoupon = """
				{
				     "orderConfirm":{
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
				 }
				""";
		orderWithoutCoupon = om.readValue(noCoupon, CreateOrderRequest.class);
	}
}