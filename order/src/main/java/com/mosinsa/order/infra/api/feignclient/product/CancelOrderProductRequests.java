package com.mosinsa.order.infra.api.feignclient.product;

import java.util.List;

public record CancelOrderProductRequests(List<CancelOrderProductRequest> cancelOrderProductRequests) {
}
