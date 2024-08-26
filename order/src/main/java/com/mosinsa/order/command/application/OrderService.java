package com.mosinsa.order.command.application;

import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.query.application.dto.OrderDetail;

public interface OrderService {

	OrderDetail order(OrderConfirmDto orderConfirmDto);

	OrderDetail cancelOrder(String orderId);
}
