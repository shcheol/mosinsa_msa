package com.mosinsa.order.dto;

import com.mosinsa.order.entity.OrderProduct;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderProductDto {

    private Long id;
    private ProductDto productDto;
    private int orderCount;

    public OrderProductDto(OrderProduct orderProduct) {
        this.id = orderProduct.getId();
        this.productDto = new ProductDto(orderProduct.getProduct());
        this.orderCount = orderProduct.getOrderCount();
    }
}
