package com.mosinsa.order.ui;

import com.mosinsa.order.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ViewOrderControllerTest extends ControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Test
	void orderedList() throws Exception {
		mockMvc.perform(get("/orders"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("size").value(2))
				.andDo(print());
	}

	@Test
	void orderDetails() throws Exception {
		String orderId = "orderId1";
		mockMvc.perform(get("/orders/" + orderId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("orderId").value(orderId))
				.andDo(print());
	}

	@Test
	void orderConfirm() throws Exception {
		mockMvc.perform(post("/orders/orderConfirm")
						.header("customer-info", """
								"{"name":"name","id":"id"}"
								""")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
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
								"""))
				.andExpect(status().is2xxSuccessful())
				.andDo(print());
	}

}