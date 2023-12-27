package com.mosinsa.order.infra.feignclient;

import java.util.List;

public record CancelOrderProductRequests(List<CancelOrderProductRequest> cancelOrderProductRequests) {
}
