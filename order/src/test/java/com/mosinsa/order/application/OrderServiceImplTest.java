package com.mosinsa.order.application;

import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OrderServiceImplTest {

	@Autowired
	OrderService orderService;



	@Test
	void order() {

//		OrderDto order = orderService.order(
//				new CreateOrderRequest("customer1",
//						List.of(new CreateOrderRequest.MyOrderProduct("productId1", 1000, 10))));
//		String orderId = order.getOrderId();


	}

	@Test
	void cancelOrder() {
	}

	@Test
	void findMyOrdersByCondition() {
	}

	@Test
	void getOrderDetails() {
	}
}