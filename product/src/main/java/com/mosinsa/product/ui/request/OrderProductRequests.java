package com.mosinsa.product.ui.request;

import java.util.List;

public record OrderProductRequests(String orderId, List<OrderProductRequest> orderProductRequests) {
}
