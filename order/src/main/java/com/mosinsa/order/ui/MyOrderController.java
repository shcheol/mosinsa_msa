package com.mosinsa.order.ui;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.query.application.OrderQueryService;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.query.application.dto.OrderSummary;
import com.mosinsa.order.ui.request.SearchCondition;
import com.mosinsa.order.ui.response.BaseResponse;
import com.mosinsa.order.ui.response.GlobalResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class MyOrderController {

    private final OrderQueryService orderQueryService;


    @GetMapping
    public ResponseEntity<BaseResponse> findMyOrders(SearchCondition condition, @PageableDefault Pageable pageable) {
        log.info("condition {}", condition);
        Page<OrderSummary> orderCustomer = orderQueryService.findMyOrdersByCondition(condition, pageable);
        return GlobalResponseEntity.success(orderCustomer);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<BaseResponse> orderDetails(@PathVariable String orderId) {
        if (!StringUtils.hasText(orderId)) {
            throw new OrderException(OrderError.VALIDATION_ERROR);
        }
        OrderDetail orderDto = orderQueryService.getOrderDetails(orderId);
        return GlobalResponseEntity.success(orderDto);
    }

}
