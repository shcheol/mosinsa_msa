package com.mosinsa.order.command.application;

import com.mosinsa.order.command.application.dto.*;
import com.mosinsa.order.command.domain.OrderId;
import com.mosinsa.order.infra.repository.OrderRepository;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class PlaceOrderServiceTest {

	@Autowired
	PlaceOrderService service;

	@Autowired
	OrderRepository repository;

	@Test
	void orderDuplicate() {

		String orderId = "orderIdTest";
		boolean exists = repository.existsById(OrderId.of(orderId));
		assertThat(exists).isFalse();

		ShippingInfoDto shippingInfoDto = new ShippingInfoDto("", new AddressDto("", "", ""), new ReceiverDto("", ""));
		CreateOrderRequest createOrderRequest = new CreateOrderRequest(new OrderConfirmDto("couponId","customerId",
				List.of(new OrderProductDto("productId1",1000,3, 3000)),shippingInfoDto,3000));

		service.order(orderId, createOrderRequest);
		boolean exists2 = repository.existsById(OrderId.of(orderId));
		assertThat(exists2).isTrue();

		service.order(orderId, createOrderRequest);
		boolean exists3 = repository.existsById(OrderId.of(orderId));
		assertThat(exists3).isTrue();
	}
}