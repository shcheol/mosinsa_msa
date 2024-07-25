package com.mosinsa.review.ui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViewReviewController.class)
@Import(ReviewPresentationObjectFactory.class)
class ViewReviewControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void productReviews() throws Exception {
		mockMvc.perform(get("/reviews"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("size").value("2"))
				.andDo(print());
	}

	@Test
	void reviewComments() throws Exception {
		mockMvc.perform(get("/reviews/reviewId/comments"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("size").value("2"))
				.andDo(print());
	}
}