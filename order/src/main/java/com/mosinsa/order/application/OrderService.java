package com.mosinsa.order.application;

import com.mosinsa.order.application.dto.CreateOrderDto;
import com.mosinsa.order.application.dto.OrderDetailDto;
import com.mosinsa.order.application.dto.OrderDto;
import com.mosinsa.order.infra.feignclient.coupon.CouponResponse;
import com.mosinsa.order.ui.request.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.function.Predicate;


public interface OrderService {

	OrderDetailDto order(CreateOrderDto createOrderDto, Predicate<CouponResponse> predicate);

	OrderDetailDto cancelOrder(String orderId);

	Page<OrderDto> findMyOrdersByCondition(SearchCondition condition, Pageable pageable);

	OrderDetailDto getOrderDetails(String orderId);

}
