package com.mosinsa.order.ui;

import com.mosinsa.order.command.application.OrderTemplate;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.infra.feignclient.HeaderConst;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import com.mosinsa.order.ui.response.BaseResponse;
import com.mosinsa.order.ui.response.GlobalResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderTemplate orderTemplate;

    @PostMapping("/orderConfirm")
    public ResponseEntity<BaseResponse> orderConfirm(@RequestBody OrderConfirmRequest orderConfirmRequest, HttpServletRequest request) {

		Map<String, Collection<String>> authMap = getAuthMap(request);

		OrderConfirmDto orderConfirmDto = orderTemplate.orderConfirm(authMap, orderConfirmRequest);

		// 주문 확인서 return
        // 멱등키 추가 전달해서 멱등성보장 + 데이터 변조 검사

        return GlobalResponseEntity.success(orderConfirmDto);

    }

    @PostMapping("/order")
    public ResponseEntity<BaseResponse> orders(@RequestBody CreateOrderRequest orderRequest, HttpServletRequest request) {

        Map<String, Collection<String>> authMap = getAuthMap(request);

		OrderDetail orderDto = orderTemplate.order(authMap, orderRequest);
		return GlobalResponseEntity.success(HttpStatus.CREATED, orderDto);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<BaseResponse> cancelOrders(@PathVariable String orderId, HttpServletRequest request) {

        Map<String, Collection<String>> authMap = getAuthMap(request);
		OrderDetail cancelOrder = orderTemplate.cancelOrder(authMap,orderId);
        return GlobalResponseEntity.success(cancelOrder);
    }

    private Map<String, Collection<String>> getAuthMap(HttpServletRequest request) {
        Map<String, Collection<String>> authMap = new HashMap<>();
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(auth)) {
            authMap.put(HttpHeaders.AUTHORIZATION, List.of(auth));
        }
        String token = request.getHeader(HeaderConst.REFRESH_TOKEN.getName());
        if (StringUtils.hasText(token)) {
            authMap.put(HeaderConst.REFRESH_TOKEN.getName(), List.of(token));
        }
        return authMap;
    }

}
