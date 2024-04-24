package com.mosinsa.order.command.application;

import com.mosinsa.order.command.domain.OrderStatus;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.query.application.dto.OrderDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CancelOrderServiceTest {

	@Autowired
	CancelOrderService service;

	@Test
	void cancelOrder() {

		OrderDetail orderId1 = service.cancelOrder("orderId1");
		OrderStatus status = orderId1.getStatus();
		assertThat(status).isEqualTo(OrderStatus.CANCELED);
	}

	@Test
	void cancelOrderNotFound() {

		assertThrows(OrderException.class, () -> service.cancelOrder("orderId1xxx"));
	}
}