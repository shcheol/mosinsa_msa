package com.mosinsa.product.ui;

import com.mosinsa.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
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
				.andExpect(jsonPath("size").value(2))
				.andDo(print());
	}

	@Test
	void findMYProducts() throws Exception {
		mockMvc.perform(get("/products/my")
						.header("customer-info", """
								"{"name":"name","id":"id"}"
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("size").value(2))
				.andDo(print());
	}

	@Test
	void productDetails() throws Exception {
		mockMvc.perform(get("/products/productId1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("test"))
				.andExpect(jsonPath("price").value(1000))
				.andExpect(jsonPath("totalStock").value(10))
				.andExpect(jsonPath("stockStatus").value("ON"))
				.andExpect(jsonPath("productId").exists())
				.andDo(print());
	}

}