package com.mosinsa.common.event;

public abstract class Event {

	private final long timestamp;

	Event(){
		this.timestamp = System.currentTimeMillis();
	}

	public long getTimestamp(){
		return timestamp;
	}
}
