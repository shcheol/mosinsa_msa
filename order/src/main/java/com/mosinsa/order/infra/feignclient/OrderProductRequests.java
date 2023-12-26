package com.mosinsa.order.infra.feignclient;

import java.util.List;

public record OrderProductRequests(List<OrderProductRequest> orderProductRequestList) {
}
