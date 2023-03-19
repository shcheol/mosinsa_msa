package com.shopping.mosinsa.order;

import com.shopping.mosinsa.controller.request.OrderCreateRequest;

import java.util.HashMap;
import java.util.Map;

public class OrderSteps {


    public static OrderCreateRequest 상품주문요청_생성(Long customerId, Long... ids) {

        Map<Long, Integer> orderProductMap = new HashMap<>();
        orderProductMap.put(ids[0], 3);
        orderProductMap.put(ids[1], 5);
        return new OrderCreateRequest(customerId, orderProductMap);
    }

}
