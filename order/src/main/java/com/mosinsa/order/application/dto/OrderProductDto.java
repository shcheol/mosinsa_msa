package com.mosinsa.order.application.dto;

import com.mosinsa.order.domain.OrderProduct;
import lombok.Value;

@Value
public class OrderProductDto {

    String productId;
    int price;
    int quantity;
	int amounts;

	public OrderProductDto(OrderProduct orderProduct){
		this.productId = orderProduct.getProductId();
		this.price = orderProduct.getPrice().getValue();
		this.quantity = orderProduct.getQuantity();
		this.amounts = orderProduct.getAmounts().getValue();
	}
}
