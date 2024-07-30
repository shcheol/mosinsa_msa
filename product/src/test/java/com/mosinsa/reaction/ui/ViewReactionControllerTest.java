package com.mosinsa.reaction.ui;

import com.mosinsa.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ViewReactionControllerTest extends ControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void total() throws Exception {

		mockMvc.perform(get("/reactions/total")
						.header("customer-info", """
								"{"name":"name","id":"id"}"
								""")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("reactionCnt").value(10))
				.andExpect(jsonPath("hasReacted").value(true))
				.andDo(print());
	}

}