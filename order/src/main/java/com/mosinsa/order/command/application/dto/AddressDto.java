package com.mosinsa.order.command.application.dto;

import com.mosinsa.order.command.domain.Address;

public record AddressDto(String zipCode, String address1, String address2) {
	public static AddressDto of(Address address){
		return new AddressDto(address.getZipCode(), address.getAddress1(), address.getAddress2());
	}
}
