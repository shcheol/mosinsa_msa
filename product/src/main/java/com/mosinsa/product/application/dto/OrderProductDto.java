package com.mosinsa.product.application.dto;

public record OrderProductDto(String orderProductId, int orderCount, ProductDetailDto productDetailDto) {
}
