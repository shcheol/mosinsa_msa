package com.mosinsa.order.infra.api.feignclient.product;

import java.util.List;

public record OrderProductRequests(String orderId, List<OrderProductRequest> orderProductRequests) {
}
