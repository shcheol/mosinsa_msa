package com.mosinsa.order.ui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MyOrderController.class)
@Import(OrderApplicationObjectFactory.class)
class MyOrderControllerTest {
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

}