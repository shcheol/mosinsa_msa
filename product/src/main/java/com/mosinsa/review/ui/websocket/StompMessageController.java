package com.mosinsa.review.ui.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class StompMessageController {

	@MessageMapping(value = "/product/{productId}")
	@SendTo("/topic/product/{productId}")
	public Map<String, String> likesTopic(@DestinationVariable(value = "productId") String productId) throws Exception {
		Thread.sleep(1000); // simulated delay

		return Map.of("productId", productId);
	}
}
