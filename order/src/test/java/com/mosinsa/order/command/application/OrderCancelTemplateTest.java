package com.mosinsa.order.command.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.command.domain.AlreadyShippedException;
import com.mosinsa.order.command.domain.OrderStatus;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.CancelOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class OrderCancelTemplateTest {

	@Autowired
	ObjectMapper om;
	@Autowired
	OrderCancelTemplate orderCancelTemplate;

	CancelOrderRequest cancelOrderRequest;

	Map<String, Collection<String>> header = Map.of();



	@Test
	void cancelOrder() {

		String orderId = "orderId1";
		OrderDetail canceledOrder = orderCancelTemplate.cancelOrder(orderId);
		assertThat(canceledOrder.getOrderId()).isEqualTo(orderId);
		assertThat(canceledOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
	}

	@Test
	void cancelOrderExternalApiFail() {
		String orderId = "orderId3";
		assertThrows(AlreadyShippedException.class, () -> orderCancelTemplate.cancelOrder(orderId));

	}

	@BeforeEach
	void init() throws JsonProcessingException {
		om = new ObjectMapper();
		String s = """
				{
				    "customerId":"customerId",
   					"orderId": "659a96b3-2efe-47a3-8d70-903a0112aed8"
				}
				""";
		cancelOrderRequest = om.readValue(s, CancelOrderRequest.class);
	}
}