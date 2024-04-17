package com.mosinsa.order.command.application.dto;

public record ShippingInfoDto(String message, AddressDto address, ReceiverDto receiver) {
}
