package com.mosinsa.order.infra.feignclient.product;

import java.util.List;

public record OrderProductRequests(String orderId, List<OrderProductRequest> orderProductRequests) {
}
