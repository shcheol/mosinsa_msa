package com.mosinsa.websocket.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Slf4j
@Controller
public class StompMessageController {

	@MessageMapping(value = "/{id}")
	@SendTo("/topic/{id}")
	public Map<String, String> likesTopic(@DestinationVariable(value = "id") String id) throws Exception {
		log.info("likesTopic");
		return Map.of("id", id);
	}
}
