package com.mosinsa.order.application;

import com.mosinsa.order.application.dto.OrderDetailDto;
import com.mosinsa.order.application.dto.OrderDto;
import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.domain.OrderId;
import com.mosinsa.order.infra.repository.OrderRepository;
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
public class OrderQueryService {
    private final OrderRepository orderRepository;

    public Page<OrderDto> findMyOrdersByCondition(SearchCondition condition, Pageable pageable) {
        if (!StringUtils.hasText(condition.getCustomerId())) {
            throw new OrderException(OrderError.VALIDATION_ERROR);
        }
        return orderRepository.findOrdersByCondition(condition, pageable).map(OrderDto::new);
    }

    public OrderDetailDto getOrderDetails(String orderId) {
        return new OrderDetailDto(orderRepository.findById(OrderId.of(orderId))
                .orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND)));
    }
}
