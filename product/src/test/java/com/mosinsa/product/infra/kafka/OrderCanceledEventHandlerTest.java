package com.mosinsa.product.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.product.ui.ProductServiceStub;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderCanceledEventHandlerTest {

	@Test
	void orderCanceled() throws JsonProcessingException {
		String data = """
				{
					"id":"id",
					"customerInfo":{"id":"customerId1","name":"name"},
					"orderProducts":[
						{"id":"9b097516","name":"SA 비건 레더 스퀘어 토트 백_블랙","quantity":1,"perPrice":48000,"totalPrice":48000,
						"options":[
							{"id":1923,"name":"YELLOW"},
							{"id":1929,"name":"M"}
						],
						"coupons":[]
						}
					]
				}
				""";
		ProductServiceStub productServiceStub = new ProductServiceStub();
		assertThat(productServiceStub.isCalled()).isFalse();


		OrderCanceledEventHandler orderCanceledEventHandler = new OrderCanceledEventHandler(productServiceStub, new ObjectMapper());
		orderCanceledEventHandler.orderCanceledEvent(data);

		assertThat(productServiceStub.isCalled()).isTrue();


	}

	@Test
	void jsonParsing() {
		String data = """
				{
					"id":"fail",
					"customerInfo":{"id":"customerId1","name":"name"},
					"orderProducts":[
						{"id":"9b097516","name":"SA 비건 레더 스퀘어 토트 백_블랙","quantity":1,"perPrice":48000,"totalPrice":48000,
						"options":[
							{"id":1923,"name":"YELLOW"},
							{"id":1929,"name":"M"}
						],
						"coupons":[]
						}
					]
				}
				""";

		OrderCanceledEventHandler orderCanceledEventHandler = new OrderCanceledEventHandler(new ProductServiceStub(), new ObjectMapper());
		assertThrows(IllegalStateException.class, () -> orderCanceledEventHandler.orderCanceledEvent(data));
	}

	@Test
	void jsonParseFail() {
		String data = """
				{"id":"48050645-13f2-4b6e-a7dc-196272f13c14","customerInfo":{"idx":"customerId1","name":"name"},"orderProducts":[{"id":"9b097516","name":"SA 비건 레더 스퀘어 토트 백_블랙","quantity":1,"perPrice":48000,"totalPrice":48000,"options":[{"id":1923,"name":"YELLOW"},{"id":1929,"name":"M"}],"coupons":[]}]}
				""";

		OrderCanceledEventHandler orderCanceledEventHandler = new OrderCanceledEventHandler(new ProductServiceStub(), new ObjectMapper());
		assertThrows(JsonProcessingException.class, () -> orderCanceledEventHandler.orderCanceledEvent(data));
	}

}