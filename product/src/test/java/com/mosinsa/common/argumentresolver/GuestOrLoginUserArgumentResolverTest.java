package com.mosinsa.common.argumentresolver;

import com.mosinsa.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GuestOrLoginUserArgumentResolverTest extends ControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Test
	void normalRequestSupportsParameter() throws Exception {
		mockMvc.perform(get("/test/success2")
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
		mockMvc.perform(get("/test/fail2")
						.header("customer-info", """
								"{"name":"name","id":"id"}"
								"""))
				.andExpect(status().isOk())
				.andExpect(content().string("{}"))
				.andDo(print());
	}

	@Test
	void resolveArgumentEmptyHeaderValue() throws Exception {
		mockMvc.perform(get("/test/success2")
						.header("customer-info", """
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value("-1"))
				.andExpect(jsonPath("name").value("guest"))
				.andDo(print());
	}

	@Test
	void resolveArgumentEmptyHeader() throws Exception {
		mockMvc.perform(get("/test/success2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value("-1"))
				.andExpect(jsonPath("name").value("guest"))
				.andDo(print());
	}
}