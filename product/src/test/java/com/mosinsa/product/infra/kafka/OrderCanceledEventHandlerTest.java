package com.mosinsa.product.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mosinsa.product.command.application.ProductService;
import com.mosinsa.product.ui.ProductPresentationObjectFactory;
import com.mosinsa.product.ui.ProductServiceStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Import(ProductPresentationObjectFactory.class)
class OrderCanceledEventHandlerTest {

	@Autowired
	ProductService productService;


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
		ProductServiceStub productServiceStub = (ProductServiceStub) productService;
		assertThat(productServiceStub.isCalled()).isFalse();

		OrderCanceledEventHandler orderCanceledEventHandler = new OrderCanceledEventHandler(new ProductServiceStub());
		orderCanceledEventHandler.orderCanceledEvent(data);

		assertThat(productServiceStub.isCalled()).isTrue();


	}

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