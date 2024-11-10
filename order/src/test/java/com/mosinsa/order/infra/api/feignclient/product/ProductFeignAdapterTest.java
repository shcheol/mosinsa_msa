package com.mosinsa.order.infra.api.feignclient.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.ApplicationTest;
import com.mosinsa.order.infra.api.ResponseResult;
import com.mosinsa.order.ui.request.OrderRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

class ProductFeignAdapterTest extends ApplicationTest {


	@Autowired
	ProductFeignAdapter adapter;

	@Test
	void orderProducts() throws JsonProcessingException {
		doNothing().when(productClient).orderProducts(any(), any());

		ResponseResult<Void> response = adapter.orderProducts("", null);
		Assertions.assertThat(response.getStatus()).isEqualTo(200);
	}

	OrderRequest getOrderConfirmRequest() throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		String s = """
				{
					"couponId":"couponId",
					"myOrderProducts":[
						{
							"productId":"productId",
							"quantity":2
						}
						],
						"shippingInfo":{
							"message":"home",
							"address":{
								"zipCode":"zipcode",
								"address1":"address1",
								"address2":"address2"
							},
							"receiver":{
								"name":"myname",
								"phoneNumber":"010-1111-1111"
							}
						}
				}
				""";
		return om.readValue(s, OrderRequest.class);
	}
}