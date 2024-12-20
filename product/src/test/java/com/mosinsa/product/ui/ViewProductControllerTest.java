package com.mosinsa.product.ui;

import com.mosinsa.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ViewProductControllerTest extends ControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void findAllProducts() throws Exception {
		mockMvc.perform(get("/products"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("totalElements").value(2))
				.andDo(print());
	}

	@Test
	void findMyProducts() throws Exception {
		mockMvc.perform(get("/products/likes")
						.header("customer-info", """
								"{"name":"name","id":"id"}"
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("totalElements").value(2))
				.andDo(print());
	}

	@Test
	void productDetails() throws Exception {
		mockMvc.perform(get("/products/01be3593"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("test"))
				.andExpect(jsonPath("price").value(1000))
				.andExpect(jsonPath("productId").exists())
				.andDo(print());
	}

}