package com.mosinsa.order.query.application;

import com.mosinsa.common.ex.OrderError;
import com.mosinsa.common.ex.OrderException;
import com.mosinsa.order.command.domain.OrderId;
import com.mosinsa.order.command.domain.OrderRepository;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.query.application.dto.OrderSummary;
import com.mosinsa.order.ui.request.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService {
    private final OrderRepository orderRepository;

    @Override
    public Page<OrderSummary> findMyOrdersByCondition(SearchCondition condition, Pageable pageable) {
        if (!StringUtils.hasText(condition.customerId())) {
            throw new OrderException(OrderError.VALIDATION_ERROR);
        }
        return orderRepository.findOrdersByCondition(condition, pageable).map(OrderSummary::new);
    }

    @Override
    public OrderDetail getOrderDetails(String orderId) {
        return new OrderDetail(orderRepository.findById(OrderId.of(orderId))
                .orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND)));
    }
}
