package com.shopping.mosinsa.dto;

import com.shopping.mosinsa.entity.OrderProduct;
import com.shopping.mosinsa.entity.OrderStatus;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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
