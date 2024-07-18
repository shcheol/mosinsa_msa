package com.mosinsa.order.ui;

import com.hcs.idempotencyapi.aop.IdempotencyApi;
import com.mosinsa.order.command.application.OrderCancelTemplate;
import com.mosinsa.order.command.application.OrderTemplate;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.query.application.OrderConfirmTemplate;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.argumentresolver.AuthMap;
import com.mosinsa.order.ui.argumentresolver.AuthToken;
import com.mosinsa.order.ui.argumentresolver.CustomerInfo;
import com.mosinsa.order.ui.argumentresolver.Login;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import com.mosinsa.order.ui.response.BaseResponse;
import com.mosinsa.order.ui.response.GlobalResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderConfirmTemplate orderConfirmTemplate;
    private final OrderTemplate orderTemplate;
    private final OrderCancelTemplate orderCancelTemplate;


    @PostMapping("/orderConfirm")
    public ResponseEntity<OrderConfirmDto> orderConfirm(@RequestBody OrderConfirmRequest orderConfirmRequest,
													 @Login CustomerInfo customerInfo,
													 @AuthMap AuthToken authMap) {

        OrderConfirmDto orderConfirmDto = orderConfirmTemplate.orderConfirm(authMap.map(), customerInfo, orderConfirmRequest);

        return ResponseEntity.ok(orderConfirmDto);

    }

    @PostMapping("/order")
    @IdempotencyApi(storeType = "redisIdempotentKeyStore")
    public ResponseEntity<OrderDetail> orders(@RequestBody CreateOrderRequest orderRequest, @AuthMap AuthToken authMap) {

        OrderDetail orderDto = orderTemplate.order(authMap.map(), orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderDetail> cancelOrders(@PathVariable String orderId) {

        OrderDetail cancelOrder = orderCancelTemplate.cancelOrder(orderId);
        return ResponseEntity.ok(cancelOrder);
    }

}
