package com.mosinsa.order.infra.feignclient.product;

import java.util.List;

public record CancelOrderProductRequests(List<CancelOrderProductRequest> cancelOrderProductRequests) {
}
