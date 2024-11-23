package com.mosinsa.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableAsync
public class EventsConfig implements ApplicationRunner {

	private final ApplicationContext applicationContext;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Events.setPublisher(applicationContext);
	}

}
