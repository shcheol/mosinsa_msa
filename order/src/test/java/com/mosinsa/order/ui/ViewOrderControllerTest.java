package com.mosinsa.order.ui;

import com.mosinsa.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
				.andExpect(jsonPath("totalElements").value(2))
				.andDo(print());
	}

	@Test
	void orderDetails() throws Exception {
		String orderId = "orderId1";
		mockMvc.perform(get("/orders/" + orderId))
				.andExpect(status().isOk())
//				.andExpect(jsonPath("id").value(orderId))
				.andDo(print());
	}
}