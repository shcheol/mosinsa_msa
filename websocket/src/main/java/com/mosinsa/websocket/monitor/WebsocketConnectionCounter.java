package com.mosinsa.websocket.monitor;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Getter
@Component
public class WebsocketConnectionCounter {

	private final AtomicInteger activeConnections = new AtomicInteger(0);

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		log.info("{} connect", event.getSource());
		activeConnections.incrementAndGet();
	}

	@EventListener()
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		log.info("{} disconnect {}", event.getSource(), event.getCloseStatus().getReason());
		activeConnections.decrementAndGet();
	}
}
