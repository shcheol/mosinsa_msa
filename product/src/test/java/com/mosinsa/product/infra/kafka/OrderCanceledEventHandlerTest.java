package com.mosinsa.product.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderCanceledEventHandlerTest {

	@Test
	void jsonParsing() throws JsonProcessingException {
		String data = """
				{
					"orderId": "orderId1",
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
		ObjectMapper objectMapper = new ObjectMapper();
		OrderCanceledEvent orderCanceledEvent = objectMapper.readValue(data, OrderCanceledEvent.class);
		assertThat(orderCanceledEvent.orderId()).isEqualTo("orderId1");
	}

}