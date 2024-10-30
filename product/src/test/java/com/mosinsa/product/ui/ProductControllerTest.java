package com.mosinsa.product.ui;

import com.mosinsa.ControllerTest;
import com.mosinsa.common.ex.ProductError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends ControllerTest {

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
								    "categoryId":"categoryId",
								    "productOptions":[
								    {
										"productOption":"SIZE",
										"productOptionValues":[
												{
													"optionsValue":"FREE",
													"changePrice":1000,
													"changeType":"PLUS",
													"stock":10
												}
											]
										}
								    ]
								}
								""")
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("id").value("id"))
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
								    "orderId":"test"
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
								    "categoryId":"categoryId",
								    "productOptions":[
								    {
										"productOption":"SIZE",
										"productOptionValues":[
												{
													"optionsValue":"FREE",
													"changePrice":1000,
													"changeType":"PLUS",
													"stock":10
												}
											]
										}
								    ]
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
								    "categoryId":"categoryId",
								    "productOptions":[
								    {
										"productOption":"SIZE",
										"productOptionValues":[
												{
													"optionsValue":"FREE",
													"changePrice":1000,
													"changeType":"PLUS",
													"stock":10
												}
											]
										}
								    ]
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
								    "categoryId":"categoryId",
								    "productOptions":[
								    {
										"productOption":"SIZE",
										"productOptionValues":[
												{
													"optionsValue":"FREE",
													"changePrice":1000,
													"changeType":"PLUS",
													"stock":10
												}
											]
										}
								    ]
								}
								""")
				)
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("result").value("error"))
				.andExpect(jsonPath("message").value(ProductError.NOT_FOUNT_PRODUCT.getMessage()))
				.andDo(print());
	}
}