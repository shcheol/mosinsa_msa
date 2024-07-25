package com.mosinsa.product.ui;

import com.mosinsa.common.ex.ProductError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(ProductPresentationObjectFactory.class)
class ProductControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void addProduct() throws Exception {
		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
								{
								    "name":"test",
								    "price":1000,
								    "stock": 10,
								    "category":"categoryId"
								}
								""")
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("name").value("test"))
				.andExpect(jsonPath("price").value(1000))
				.andExpect(jsonPath("totalStock").value(10))
				.andExpect(jsonPath("stockStatus").value("ON"))
				.andExpect(jsonPath("productId").exists())
				.andDo(print());
	}

	@Test
	void orderProducts() throws Exception {
		mockMvc.perform(post("/products/order")
						.header("customer-info", """
								"{"name":"name","id":"id"}"
								""")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
								{
								    "name":"test",
								    "price":1000,
								    "stock": 10
								}
								""")
				)
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	void globalException() throws Exception {
		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
								{
								    "name":"error",
								    "price":1000,
								    "stock": 10,
								    "category":"categoryId"
								}
								""")
				)
				.andExpect(status().is5xxServerError())
				.andDo(print());
	}

	@Test
	void globalProductException5xx() throws Exception {
		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
								{
								    "name":"productException5xx",
								    "price":1000,
								    "stock": 10,
								    "category":"categoryId"
								}
								""")
				)
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("result").value("error"))
				.andExpect(jsonPath("message").value(ProductError.INTERNAL_SERVER_ERROR.getMessage()))
				.andDo(print());
	}

	@Test
	void globalProductException4xx() throws Exception {
		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
								{
								    "name":"productException4xx",
								    "price":1000,
								    "stock": 10,
								    "category":"categoryId"
								}
								""")
				)
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("result").value("error"))
				.andExpect(jsonPath("message").value(ProductError.NOT_FOUNT_PRODUCT.getMessage()))
				.andDo(print());
	}
}