package com.mosinsa.product.ui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(ProductObjectFactory.class)
class ProductControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void addProduct() throws Exception {
		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
								{
								    "name":"비슬로우",
								    "price":62910,
								    "stock": 999999999,
								    "category":"categoryId3"
								}
								""")
				)
				.andExpect(status().isCreated())
				.andDo(print());
	}

}