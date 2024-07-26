package com.mosinsa.reaction.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

class KafkaEventsTest {

	@Test
	void test() {
		try {
			ObjectMapper om = new ObjectMapper();
			om.writeValueAsString(null);
			om.writeValueAsString("");
			om.writeValueAsString(StringEscapeUtils.escapeJava("qwer"));
			om.writeValueAsString(StringEscapeUtils.unescapeJava("qwer"));
		}catch (JsonProcessingException ex){
			ex.printStackTrace();
		}
	}


}