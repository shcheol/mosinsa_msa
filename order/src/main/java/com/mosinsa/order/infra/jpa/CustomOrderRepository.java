package com.mosinsa.order.infra.jpa;

import com.mosinsa.order.ui.request.SearchCondition;
import com.mosinsa.order.command.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomOrderRepository{

	Page<Order> findOrdersByCondition(SearchCondition condition, Pageable pageable);

}
