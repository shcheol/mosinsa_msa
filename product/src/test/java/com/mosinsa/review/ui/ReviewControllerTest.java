package com.mosinsa.review.ui;

import com.mosinsa.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends ControllerTest {

	@Autowired
	MockMvc mockMvc;


	@Test
	void writeReview() throws Exception {
		mockMvc.perform(post("/reviews")
						.header("customer-info", """
								"{"name":"name","id":"id"}"
								""")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
								{

								}
								""")
				)
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Test
	void deleteReview() throws Exception {
		mockMvc.perform(post("/reviews/reviewId/delete")
						.header("customer-info", """
								"{"name":"name","id":"id"}"
								""")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
								{

								}
								""")
				)
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	void writeComment() throws Exception {
		mockMvc.perform(post("/reviews/reviewId/comments").header("customer-info", """
								"{"name":"name","id":"id"}"
								""")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
								{

								}
								""")
				)
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Test
	void deleteComment() throws Exception {
		mockMvc.perform(post("/reviews/reviewId/comments/commentId/delete").header("customer-info", """
								"{"name":"name","id":"id"}"
								""")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
								{

								}
								""")
				)
				.andExpect(status().isOk())
				.andDo(print());
	}

}