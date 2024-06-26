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
    public ResponseEntity<BaseResponse> orderConfirm(@RequestBody OrderConfirmRequest orderConfirmRequest,
													 @Login CustomerInfo customerInfo,
													 @AuthMap AuthToken authMap) {

        OrderConfirmDto orderConfirmDto = orderConfirmTemplate.orderConfirm(authMap.map(), customerInfo, orderConfirmRequest);

        return GlobalResponseEntity.success(orderConfirmDto);

    }

    @PostMapping("/order")
    @IdempotencyApi(storeType = "redisIdempotentKeyStore")
    public ResponseEntity<BaseResponse> orders(@RequestBody CreateOrderRequest orderRequest, @AuthMap AuthToken authMap) {

        OrderDetail orderDto = orderTemplate.order(authMap.map(), orderRequest);
        return GlobalResponseEntity.success(HttpStatus.CREATED, orderDto);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<BaseResponse> cancelOrders(@PathVariable String orderId) {

        OrderDetail cancelOrder = orderCancelTemplate.cancelOrder(orderId);
        return GlobalResponseEntity.success(cancelOrder);
    }

}
