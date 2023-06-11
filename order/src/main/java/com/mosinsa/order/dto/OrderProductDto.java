package com.mosinsa.order.dto;

import com.mosinsa.order.entity.OrderProduct;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderProductDto {

    private Long id;
    private int orderCount;
    private ProductDto productDto;

    public OrderProductDto(Long orderProductId, int orderCount, ProductDto productDto) {
        this.id = orderProductId;
        this.orderCount = orderCount;
        this.productDto = productDto;
    }
}
