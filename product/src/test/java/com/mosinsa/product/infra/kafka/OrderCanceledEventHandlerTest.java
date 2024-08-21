package com.mosinsa.product.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mosinsa.product.ui.ProductServiceStub;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderCanceledEventHandlerTest {

	@Test
	void orderCanceled() throws JsonProcessingException {
		String data = """
				{
					"orderId": "orderId",
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

		ProductServiceStub productServiceStub = new ProductServiceStub();
		assertThat(productServiceStub.isCalled()).isFalse();


		OrderCanceledEventHandler orderCanceledEventHandler = new OrderCanceledEventHandler(productServiceStub);
		orderCanceledEventHandler.orderCanceledEvent(data);

		assertThat(productServiceStub.isCalled()).isTrue();


	}

	@Test
	void jsonParsing() {
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
	void jsonParseFail() {
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
		assertThrows(JsonProcessingException.class, () -> orderCanceledEventHandler.orderCanceledEvent(data));
	}

}