package com.mosinsa.order.command.application;

import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderId;
import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.command.domain.OrderRepository;
import com.mosinsa.order.query.application.dto.OrderDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelOrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderDetail cancelOrder(String orderId) {
        Order findOrder = orderRepository.findById(OrderId.of(orderId))
                .orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND));
        findOrder.cancelOrder();
        return new OrderDetail(findOrder);
    }
}
