package com.mosinsa.order.command.application;

import com.mosinsa.order.command.application.dto.OrderInfo;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.OrderRequest;

public interface OrderService {

    OrderDetail order(OrderInfo orderRequest);

    OrderDetail cancelOrder(String orderId);
}
