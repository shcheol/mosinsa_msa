package com.mosinsa.order.db.repository;

import com.mosinsa.order.controller.request.SearchCondition;
import com.mosinsa.order.db.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomOrderRepository{

	Page<Order> findOrdersByCondition(SearchCondition condition, Pageable pageable);

}
