package com.mosinsa.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class OrderProductDto {

    private String orderProductId;
    private int orderCount;
    private ProductDto productDto;

    public OrderProductDto(String orderProductId, int orderCount, ProductDto productDto) {
        this.orderProductId = orderProductId;
        this.orderCount = orderCount;
        this.productDto = productDto;
    }
}
