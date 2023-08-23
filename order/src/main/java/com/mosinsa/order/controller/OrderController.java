package com.mosinsa.order.controller;

import com.mosinsa.order.controller.request.OrderCancelRequest;
import com.mosinsa.order.controller.request.OrderCreateRequest;
import com.mosinsa.order.controller.request.SearchCondition;
import com.mosinsa.order.db.dto.OrderDto;
import com.mosinsa.order.service.OrderService;
import com.mosinsa.order.service.feignclient.HeaderConst;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderDto>> getOrderByCustomer(@PathVariable String customerId){

        List<OrderDto> orderCustomer = orderService.getOrderCustomer(customerId);

        return ResponseEntity.ok().body(orderCustomer);
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<OrderDto>> findOrders(SearchCondition condition, @PageableDefault Pageable pageable){
		log.info("condition {}", condition);
        Page<OrderDto> orderCustomer = orderService.findOrdersByCondition(condition, pageable);
        return ResponseEntity.ok().body(orderCustomer);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId){

        OrderDto orderDto = orderService.findOrderById(orderId);

        return ResponseEntity.ok().body(orderDto);
    }


    @PostMapping("/orders")
    public ResponseEntity<OrderDto> orders(@RequestBody OrderCreateRequest orderRequest, HttpServletRequest request){

        OrderDto orderDto = orderService.order(orderRequest.getCustomerId(), orderRequest.getProducts(), getAuthMap(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    @PatchMapping("/orders")
    public ResponseEntity<Long> cancelOrders(@RequestBody OrderCancelRequest cancelRequest, HttpServletRequest request){


        Assert.isTrue(cancelRequest.getCustomerId()!= null && !cancelRequest.getCustomerId().isBlank(),"고객 id가 없습니다.");
        Assert.isTrue(cancelRequest.getOrderId()!= null && cancelRequest.getOrderId()>0,"주문 id가 없습니다.");

        orderService.cancelOrder(cancelRequest.getCustomerId(), cancelRequest.getOrderId(), getAuthMap(request));
        return ResponseEntity.ok().build();
    }

	private Map<String, Collection<String>> getAuthMap(HttpServletRequest request){
		Map<String, Collection<String>> authMap = new HashMap<>();
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(StringUtils.hasText(header)){
			authMap.put(HttpHeaders.AUTHORIZATION, List.of(header));
		}
		String header1 = request.getHeader(HeaderConst.REFRESH_TOKEN.getName());
		if(StringUtils.hasText(header1)){
			authMap.put(HeaderConst.REFRESH_TOKEN.getName(), List.of(header1));
		}
		return authMap;
	}

}
