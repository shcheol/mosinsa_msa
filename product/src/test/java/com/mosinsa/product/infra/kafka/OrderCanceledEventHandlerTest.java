package com.mosinsa.product.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.product.command.application.ProductService;
import com.mosinsa.product.query.ProductDetailDto;
import com.mosinsa.product.ui.ProductServiceStub;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class OrderCanceledEventHandlerTest {

	@Test
	void jsonParsing() throws JsonProcessingException {
		String data = """
				{
					"orderId": "fail",
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

		OrderCanceledEventHandler orderCanceledEventHandler = new OrderCanceledEventHandler(new ProductServiceStub());
		assertThrows(IllegalStateException.class, () -> orderCanceledEventHandler.orderCanceledEvent(data));
	}

	@Test
	void jsonParseFail() throws JsonProcessingException {
		String data = """
				{
					"orderIdx": "orderId1",
					"customerIdx": "customer1",
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

		OrderCanceledEventHandler orderCanceledEventHandler = new OrderCanceledEventHandler(new ProductServiceStub());
		assertThrows(JsonProcessingException.class, ()->orderCanceledEventHandler.orderCanceledEvent(data));
	}

}