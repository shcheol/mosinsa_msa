package com.shopping.mosinsa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int totalPrice;

    private int requestCount;

    public OrderProduct(Order order, Product product, int requestCount) {
        this.order = order;
        this.product = product;
        this.requestCount = requestCount;
        this.totalPrice = product.getPrice()*requestCount;

        order.getOrderProducts().add(this);
    }
}
