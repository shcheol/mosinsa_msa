package com.mosinsa.websocket.monitor;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class WebsocketMonitorConfig {

	@Bean
	public MeterBinder activeConnections(WebsocketConnectionCounter websocketConnectionCounter) {
		return registry -> Gauge.builder("websocket.connections.active",
				() -> {
					log.info("call gauge");
					return websocketConnectionCounter.getActiveConnections().get();
				}).register(registry);
	}
}
