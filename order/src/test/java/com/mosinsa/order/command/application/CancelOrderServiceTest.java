package com.mosinsa.order.command.application;

import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderStatus;
import com.mosinsa.order.common.ex.OrderException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CancelOrderServiceTest {

	@Autowired
	CancelOrderService service;

	@Test
	void cancelOrder() {
		Order orderId1 = service.cancelOrder("orderId1");
		OrderStatus status1 = orderId1.getStatus();
		assertThat(status1).isEqualTo(OrderStatus.CANCELED);

		Order orderId2 = service.cancelOrder("orderId2");
		OrderStatus status2 = orderId2.getStatus();
		assertThat(status2).isEqualTo(OrderStatus.CANCELED);

	}

	@Test
	void cancelOrderNotFound() {

		assertThrows(OrderException.class, () -> service.cancelOrder("orderId1xxx"));
	}
}