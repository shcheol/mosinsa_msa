package com.shopping.mosinsa.controller;

import com.shopping.mosinsa.controller.request.OrderCancelRequest;
import com.shopping.mosinsa.controller.request.OrderCreateRequest;
import com.shopping.mosinsa.dto.OrderDto;
import com.shopping.mosinsa.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

//    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrderByCustomer(@RequestParam Long customerId){

        List<OrderDto> orderCustomer = orderService.getOrderCustomer(customerId);

        return ResponseEntity.ok().body(orderCustomer);
    }

    @GetMapping
    public ResponseEntity<Page<OrderDto>> getOrders(@PageableDefault Pageable pageable){
        Page<OrderDto> orderCustomer = orderService.getOrders(pageable);
        return ResponseEntity.ok().body(orderCustomer);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId){

        OrderDto orderDto = orderService.getOrder(orderId);

        return ResponseEntity.ok().body(orderDto);
    }


    @PostMapping
    public ResponseEntity<OrderDto> orders(@RequestBody OrderCreateRequest request){

        OrderDto orderDto = orderService.order(request.getCustomerId(), request.getProducts());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    @PatchMapping
    public ResponseEntity<Long> cancelOrders(@RequestBody OrderCancelRequest request){


        Assert.isTrue(request.getCustomerId()!= null && request.getCustomerId()>0,"고객 id가 없습니다.");
        Assert.isTrue(request.getOrderId()!= null && request.getOrderId()>0,"주문 id가 없습니다.");

        orderService.cancelOrder(request.getCustomerId(), request.getOrderId());
        return ResponseEntity.ok().build();
    }


}
