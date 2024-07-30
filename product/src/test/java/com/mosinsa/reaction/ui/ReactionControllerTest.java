package com.mosinsa.reaction.ui;

import com.mosinsa.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReactionControllerTest extends ControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void addUserReaction() throws Exception {
		mockMvc.perform(post("/reactions")
						.header("customer-info", """
								"{"name":"name","id":"id"}"
								""")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
								{
								}
								""")
				)
				.andExpect(status().isOk());
	}

	@Test
	void cancelUserReaction() throws Exception {
		mockMvc.perform(post("/reactions/cancel")
						.header("customer-info", """
								"{"name":"name","id":"id"}"
								""")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
								{

								}
								""")
				)
				.andExpect(status().isOk());
	}

}