package com.mosinsa.category.ui;

import com.mosinsa.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest extends ControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void getCategories() throws Exception {
		mockMvc.perform(get("/category"))
				.andExpect(status().isOk())
				.andExpect(content().json("""
						[{"categoryId":"testId","name":"testName"}]
						"""))
				.andDo(print());
	}

}