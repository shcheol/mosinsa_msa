package com.mosinsa.order.ui.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MyOrderProduct(String productId, Integer quantity) {

}
