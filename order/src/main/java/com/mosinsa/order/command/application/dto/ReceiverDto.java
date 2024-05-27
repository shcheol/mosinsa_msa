package com.mosinsa.order.command.application.dto;

import com.mosinsa.order.command.domain.Receiver;

public record ReceiverDto(String name, String phoneNumber) {
	public static ReceiverDto of(Receiver receiver){
		return new ReceiverDto(receiver.getName(), receiver.getPhoneNumber());
	}
}
