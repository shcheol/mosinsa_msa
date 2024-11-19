package com.mosinsa.common.event;

import org.springframework.context.ApplicationEventPublisher;

public class Events {
	private static ApplicationEventPublisher publisher;

	protected static void setPublisher(ApplicationEventPublisher publisher){
		Events.publisher = publisher;
	}

	public static void raise(Object event){
		if(publisher==null){
			throw new IllegalStateException("ApplicationEventPublisher not set yet");
		}
		publisher.publishEvent(event);
	}
}
