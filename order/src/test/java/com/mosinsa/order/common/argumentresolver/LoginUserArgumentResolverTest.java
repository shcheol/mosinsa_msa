package com.mosinsa.order.common.argumentresolver;

import com.mosinsa.order.ControllerTest;
import com.mosinsa.order.code.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginUserArgumentResolverTest extends ControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void normalRequestSupportsParameter() throws Exception {
		mockMvc.perform(get("/test/success")
						.header("customer-info", """
								"{"name":"name","id":"id"}"
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value("id"))
				.andExpect(jsonPath("name").value("name"))
				.andDo(print());
	}

	@Test
	void badControllerSupportsParameter() throws Exception {
		mockMvc.perform(get("/test/fail")
						.header("customer-info", """
								"{"name":"name","id":"id"}"
								"""))
				.andExpect(status().isOk())
				.andExpect(content().string("{}"))
				.andDo(print());
	}

	@Test
	void resolveArgumentEmptyHeaderValue() throws Exception {
		mockMvc.perform(get("/test/success")
						.header("customer-info", """
								"""))
				.andExpect(status().is4xxClientError())
				.andDo(print());
	}

	@Test
	void resolveArgumentEmptyHeader() throws Exception {
		mockMvc.perform(get("/test/success"))
				.andExpect(status().is4xxClientError())
				.andDo(print());
	}
}