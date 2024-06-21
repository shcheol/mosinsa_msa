package com.mosinsa.review.infra.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageSubscriber implements MessageListener {

	private final SimpMessagingTemplate template;
	@Override
	public void onMessage(Message message, byte[] pattern) {
		log.info("subscribe");

		template.convertAndSend("/topic/product/" + new String(pattern), message.toString());
	}
}
