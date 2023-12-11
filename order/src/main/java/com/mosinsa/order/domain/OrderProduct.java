package com.mosinsa.order.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {
    private String productId;

    private Money price;

    private int quantity;

    private Money amounts;

    public static OrderProduct createOrderProduct(String productId, Money price, int quantity) {

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.productId = productId;
        orderProduct.price = price;
        orderProduct.quantity = quantity;
        orderProduct.amounts = price.multiply(quantity);

        return orderProduct;
    }

}
