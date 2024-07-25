package com.mosinsa.category;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@Import(CategoryObjectFactory.class)
class CategoryControllerTest {

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