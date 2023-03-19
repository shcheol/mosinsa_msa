package com.shopping.mosinsa.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{customerId}")
    public ResponseEntity<List<OrderDto>> getOrders(@PathVariable Long customerId){

        List<OrderDto> orderCustomer = orderService.getOrderCustomer(customerId);

        return ResponseEntity.ok().body(orderCustomer);
    }

    @GetMapping
    public ResponseEntity<Page<OrderDto>> getOrders(@PageableDefault Pageable pageable){
        Page<OrderDto> orderCustomer = orderService.getOrders(pageable);
        return ResponseEntity.ok().body(orderCustomer);
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<OrderDto> orders(@PathVariable Long customerId, @RequestBody OrderCreateRequest request){

        OrderDto orderDto = orderService.order(customerId, request.getProducts());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<Void> cancelOrders(@PathVariable Long customerId, @RequestParam Long orderId){

        orderService.cancelOrder(customerId, orderId);
        return ResponseEntity.ok().build();
    }


}
