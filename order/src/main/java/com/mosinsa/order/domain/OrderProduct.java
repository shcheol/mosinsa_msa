package com.mosinsa.order.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {
    private String productId;

    private Money price;

    private int quantity;

    private Money amounts;

    public static OrderProduct createOrderProduct(String productId, int price, int quantity) {

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.productId = productId;
        orderProduct.price = Money.of(price);
        orderProduct.quantity = quantity;
        orderProduct.amounts = orderProduct.price.multiply(quantity);

        return orderProduct;
    }

}
