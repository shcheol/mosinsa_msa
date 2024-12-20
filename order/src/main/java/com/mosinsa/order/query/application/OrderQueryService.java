package com.mosinsa.order.query.application;

import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.query.application.dto.OrderSummary;
import com.mosinsa.order.ui.request.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderQueryService {
    Page<OrderSummary> findMyOrdersByCondition(SearchCondition condition, Pageable pageable);

    OrderDetail getOrderDetails(String orderId);

}
