package com.mosinsa.promotion.infra.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomPageImplTest {

	@Test
	void construct() throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		String message = """
				{
				    "content":[],
					"empty": false,
					"first": true,
					"last":true,
					"number":0,
					"numberOfElements":5,
					"pageable": {
						"sort": {
							"empty": true,
							"sorted": false,
							"unsorted": true
						},
						"offset": 0,
						"pageNumber": 0,
						"pageSize": 10,
						"paged": true,
						"unpaged": false
					},
					"size":10,
					"totalElements":5,
					"totalPages":1
				}
				""";

		CustomPageImpl customPage = om.readValue(message, CustomPageImpl.class);


		assertThat(customPage.getContent()).isEmpty();
		assertThat(customPage.getTotalPages()).isEqualTo(1);
		assertThat(customPage.getPageable().getPageSize()).isEqualTo(10);
	}

}