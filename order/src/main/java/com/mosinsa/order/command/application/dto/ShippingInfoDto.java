package com.mosinsa.order.command.application.dto;

import com.mosinsa.order.command.domain.ShippingInfo;

public record ShippingInfoDto(String message, AddressDto address, ReceiverDto receiver) {
	public static ShippingInfoDto of(ShippingInfo shippingInfo){
		return new ShippingInfoDto(shippingInfo.getMessage(), AddressDto.of(shippingInfo.getAddress()), ReceiverDto.of(shippingInfo.getReceiver()));
	}
}
